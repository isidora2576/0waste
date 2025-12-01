package com.evaluacion.a0waste_G5_final.Viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.evaluacion.a0waste_G5_final.Data.SessionManager
import com.evaluacion.a0waste_G5_final.Service.RetrofitClient
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class WasteViewModelTest {

    // Regla para testing de LiveData/StateFlow
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Regla para coroutines
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    // Mocks
    @MockK
    private lateinit var mockSessionManager: SessionManager

    @MockK
    private lateinit var mockRetrofitClient: com.evaluacion.a0waste_G5_final.Service.ApiService

    private lateinit var viewModel: WasteViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        // Configurar mocks
        every { mockSessionManager.getUserId() } returns 123L
        every { mockSessionManager.isLoggedIn() } returns true

        // Mock de RetrofitClient
        mockkObject(RetrofitClient)
        every { RetrofitClient.apiService } returns mockRetrofitClient

        // Crear ViewModel con Application mockeada
        val mockApplication = mockk<android.app.Application>()
        val mockContext = mockk<Context>()
        val mockSharedPreferences = mockk<SharedPreferences>()
        every { mockApplication.applicationContext } returns mockContext
        every { mockContext.getSharedPreferences(any(), any()) } returns mockSharedPreferences
        every { mockSharedPreferences.getBoolean(any(), any()) } returns true
        every { mockSharedPreferences.getLong(any(), any()) } returns 123L

        viewModel = WasteViewModel(mockApplication)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `test cargarPuntosDesdeApi cuando usuario logueado`() = testScope.runTest {
        // Given - Configurar escenario
        val puntosEsperados = 50
        val responseMock = mockk<Response<Any>>()

        coEvery { mockRetrofitClient.obtenerUsuario(123L) } returns responseMock
        every { responseMock.isSuccessful } returns true
        every { responseMock.body() } returns mapOf(
            "puntosActuales" to puntosEsperados.toDouble(),
            "id" to 123L,
            "nombreCompleto" to "Test User"
        )

        // When - Ejecutar acción
        viewModel.cargarPuntosDesdeApi()
        advanceUntilIdle() // Esperar a que termine la coroutine

        // Then - Verificar resultado
        assertEquals(puntosEsperados, viewModel.getPoints())
    }

    @Test
    fun `test agregarPuntosApi cuando usuario logueado`() = testScope.runTest {
        // Given
        val responseMock = mockk<Response<Any>>()
        coEvery { mockRetrofitClient.agregarPuntos(any()) } returns responseMock
        every { responseMock.isSuccessful } returns true

        // Simular que obtenerUsuario retorna 100 puntos iniciales
        val usuarioResponse = mockk<Response<Any>>()
        coEvery { mockRetrofitClient.obtenerUsuario(any()) } returns usuarioResponse
        every { usuarioResponse.isSuccessful } returns true
        every { usuarioResponse.body() } returns mapOf(
            "puntosActuales" to 100.0,
            "id" to 123L
        )

        // When
        viewModel.agregarPuntosApi(5, "Test")
        advanceUntilIdle()

        // Then - Debería haber llamado a la API
        coVerify { mockRetrofitClient.agregarPuntos(any()) }
    }

    @Test
    fun `test getPoints devuelve puntos actuales`() {
        // Configurar puntos iniciales
        testScope.runTest {
            // Simular respuesta de API
            val responseMock = mockk<Response<Any>>()
            coEvery { mockRetrofitClient.obtenerUsuario(any()) } returns responseMock
            every { responseMock.isSuccessful } returns true
            every { responseMock.body() } returns mapOf(
                "puntosActuales" to 75.0
            )

            viewModel.cargarPuntosDesdeApi()
            advanceUntilIdle()

            assertEquals(75, viewModel.getPoints())
        }
    }

    @Test
    fun `test obtenerUserId devuelve id del usuario`() {
        // El ViewModel debería usar el SessionManager para obtener el ID
        assertEquals(123L, viewModel.obtenerUserId())
    }

    @Test
    fun `test hayUsuarioLogueado devuelve true cuando hay sesion`() {
        assertTrue(viewModel.hayUsuarioLogueado())
    }
}
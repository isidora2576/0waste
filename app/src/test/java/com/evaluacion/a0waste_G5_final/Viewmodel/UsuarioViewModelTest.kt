package com.evaluacion.a0waste_G5_final.Viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.evaluacion.a0waste_G5_final.Data.SessionManager
import com.evaluacion.a0waste_G5_final.Model.UsuarioRequest
import com.evaluacion.a0waste_G5_final.Service.RetrofitClient
import io.mockk.*
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class UsuarioViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private lateinit var viewModel: UsuarioViewModel
    private lateinit var mockSessionManager: SessionManager
    private lateinit var mockApiService: com.evaluacion.a0waste_G5_final.Service.ApiService

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        // Crear mocks
        mockSessionManager = mockk()
        mockApiService = mockk()

        // Mock RetrofitClient
        mockkObject(RetrofitClient)
        every { RetrofitClient.apiService } returns mockApiService

        viewModel = UsuarioViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `test validarRegistroLocal con datos validos`() = testScope.runTest {
        // Given - Datos válidos
        val nombre = "Juan Pérez"
        val email = "juan@test.com"
        val password = "123456"
        val direccion = "Calle 123"
        val telefono = "912345678"
        val tipoReciclador = "Principiante"

        // When
        val resultado = viewModel.validarRegistroLocal(
            nombre, email, password, direccion, telefono,
            tipoReciclador, true, true
        )

        // Then
        assertTrue(resultado)

        // Verificar que no hay errores
        val errores = viewModel.erroresRegistro.value
        assertTrue(errores.isEmpty())
    }

    @Test
    fun `test validarRegistroLocal con email invalido`() = testScope.runTest {
        // Given - Email inválido
        val emailInvalido = "email-sin-arroba"

        // When
        val resultado = viewModel.validarRegistroLocal(
            "Nombre", emailInvalido, "123456", "Dirección",
            "912345678", "Principiante", true, true
        )

        // Then
        assertFalse(resultado)

        val errores = viewModel.erroresRegistro.value
        assertTrue(errores.containsKey("email"))
        assertEquals("Email inválido", errores["email"])
    }

    @Test
    fun `test validarLoginLocal con datos validos`() = testScope.runTest {
        // Given
        val email = "test@mail.com"
        val password = "123456"

        // When
        val resultado = viewModel.validarLoginLocal(email, password)

        // Then
        assertTrue(resultado)
        assertTrue(viewModel.erroresLogin.value.isEmpty())
    }

    @Test
    fun `test registrarUsuario llama a API`() = testScope.runTest {
        // Given
        val usuarioRequest = UsuarioRequest(
            nombreCompleto = "Test User",
            email = "test@mail.com",
            password = "123456",
            direccion = "Test 123",
            telefono = "912345678",
            tipoReciclador = "PRINCIPIANTE",
            materialesInteres = emptyList(),
            aceptaTerminos = true,
            permisoCamara = true
        )

        val responseMock = mockk<Response<Any>>()
        every { responseMock.isSuccessful } returns true
        coEvery { mockApiService.registrarUsuario(any()) } returns responseMock

        // When
        viewModel.registrarUsuario(usuarioRequest)
        advanceUntilIdle()

        // Then - Debería llamar a la API
        coVerify { mockApiService.registrarUsuario(usuarioRequest) }
    }

    @Test
    fun `test limpiarErroresRegistro limpia los errores`() = testScope.runTest {
        // Given - Primero crear errores
        viewModel.validarRegistroLocal(
            "", "", "", "", "", "", false, false
        )

        // Verificar que hay errores
        assertTrue(viewModel.erroresRegistro.value.isNotEmpty())

        // When
        viewModel.limpiarErroresRegistro()

        // Then
        assertTrue(viewModel.erroresRegistro.value.isEmpty())
    }
}
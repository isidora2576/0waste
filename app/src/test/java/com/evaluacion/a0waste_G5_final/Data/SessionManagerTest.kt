package com.evaluacion.a0waste_G5_final.Data

import android.content.Context
import android.content.SharedPreferences
import com.evaluacion.a0waste_G5_final.Data.SessionManager
import io.mockk.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SessionManagerTest {

    private lateinit var sessionManager: SessionManager
    private lateinit var mockContext: Context
    private lateinit var mockSharedPreferences: SharedPreferences
    private lateinit var mockEditor: SharedPreferences.Editor

    @Before
    fun setUp() {
        // Crear mocks
        mockContext = mockk()
        mockSharedPreferences = mockk()
        mockEditor = mockk()

        // Configurar comportamiento
        every { mockContext.getSharedPreferences(any(), any()) } returns mockSharedPreferences
        every { mockSharedPreferences.edit() } returns mockEditor
        every { mockEditor.putLong(any(), any()) } returns mockEditor
        every { mockEditor.putString(any(), any()) } returns mockEditor
        every { mockEditor.putBoolean(any(), any()) } returns mockEditor
        every { mockEditor.clear() } returns mockEditor
        every { mockEditor.apply() } just Runs

        sessionManager = SessionManager(mockContext)
    }

    @Test
    fun `test saveUserSession guarda datos correctamente`() {
        // Given
        val userId = 123L
        val email = "test@mail.com"
        val name = "Test User"

        // When
        sessionManager.saveUserSession(userId, email, name)

        // Then - Debería guardar los tres valores
        verify {
            mockEditor.putLong("user_id", userId)
            mockEditor.putString("user_email", email)
            mockEditor.putString("user_name", name)
            mockEditor.putBoolean("is_logged_in", true)
            mockEditor.apply()
        }
    }

    @Test
    fun `test getUserId devuelve id guardado`() {
        // Given
        val expectedId = 456L
        every { mockSharedPreferences.getLong("user_id", 0L) } returns expectedId

        // When
        val result = sessionManager.getUserId()

        // Then
        assertEquals(expectedId, result)
    }

    @Test
    fun `test isLoggedIn devuelve true cuando hay sesion`() {
        // Given
        every { mockSharedPreferences.getBoolean("is_logged_in", false) } returns true
        every { mockSharedPreferences.getLong("user_id", 0L) } returns 123L

        // When
        val result = sessionManager.isLoggedIn()

        // Then
        assertTrue(result)
    }

    @Test
    fun `test logout limpia la sesion`() {
        // When
        sessionManager.logout()

        // Then
        verify { mockEditor.clear() }
        verify { mockEditor.apply() }
    }
}
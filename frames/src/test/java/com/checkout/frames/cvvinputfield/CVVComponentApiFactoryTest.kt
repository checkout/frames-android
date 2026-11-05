package com.checkout.frames.cvvinputfield

import android.content.Context
import com.checkout.base.error.CheckoutError
import com.checkout.base.model.Environment
import com.checkout.frames.cvvinputfield.api.InternalCVVComponentApi
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

internal class CVVComponentApiFactoryTest {

    private lateinit var publicKey: String
    private lateinit var environment: Environment
    private lateinit var context: Context

    @Before
    fun setUp() {
        publicKey = "test_public_key"
        environment = Environment.SANDBOX
        context = mockk()
    }

    @Test
    fun `when CVVComponentApiFactory is created then InternalCVVComponentApi is correctly created`() {
        // When
        val cvvComponentApi = CVVComponentApiFactory.create(publicKey, environment, context, null)

        // Then
        assertEquals(InternalCVVComponentApi::class.java, cvvComponentApi.javaClass)
    }

    @Test
    fun `when CVVComponentApiFactory is created with blank base URL prefix then configuration invalid is thrown`() {
        val error = assertThrows(CheckoutError::class.java) {
            CVVComponentApiFactory.create(publicKey, environment, context, "")
        }

        assertEquals("CONFIGURATION_INVALID", error.errorCode)
    }
}

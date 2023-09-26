package com.checkout.frames.cvvcomponent

import android.content.Context
import com.checkout.base.model.Environment
import com.checkout.frames.cvvcomponent.api.InternalCVVComponentApi
import io.mockk.mockk
import org.junit.Assert.assertEquals
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
        val cvvComponentApi = CVVComponentApiFactory.create(publicKey, environment, context)

        // Then
        assertEquals(InternalCVVComponentApi::class.java, cvvComponentApi.javaClass)
    }
}

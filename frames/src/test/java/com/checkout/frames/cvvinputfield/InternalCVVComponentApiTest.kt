package com.checkout.frames.cvvinputfield

import android.content.Context
import com.checkout.base.model.Environment
import com.checkout.frames.cvvinputfield.api.InternalCVVComponentApi
import com.checkout.frames.cvvinputfield.api.InternalCVVComponentMediator
import com.checkout.frames.cvvinputfield.models.CVVComponentConfig
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.spyk
import org.amshove.kluent.internal.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class InternalCVVComponentApiTest {
    private lateinit var publicKey: String
    private lateinit var environment: Environment

    @RelaxedMockK
    private lateinit var mockContext: Context

    @RelaxedMockK
    private lateinit var cvvComponentConfig: CVVComponentConfig

    @BeforeEach
    fun setUp() {
        publicKey = "test_key"
        environment = Environment.SANDBOX
    }

    @Test
    fun `when createComponentMediator is invoked then InternalCVVComponentMediator is correctly created`() {
        // Given
        val internalCVVComponentApi = spyk(
            InternalCVVComponentApi(
                publicKey = "your_public_key",
                environment = Environment.SANDBOX,
                context = mockContext,
            ),
        )

        // When
        val componentMediator = internalCVVComponentApi.createComponentMediator(cvvComponentConfig)

        // Then
        assertEquals(InternalCVVComponentMediator::class.java, componentMediator.javaClass)
    }
}

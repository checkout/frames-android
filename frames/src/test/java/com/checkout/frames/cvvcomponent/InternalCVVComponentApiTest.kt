package com.checkout.frames.cvvcomponent

import android.content.Context
import com.checkout.base.model.Environment
import com.checkout.frames.cvvcomponent.api.CVVComponentApi
import com.checkout.frames.cvvcomponent.api.InternalCVVComponentApi
import com.checkout.frames.cvvcomponent.api.InternalCVVComponentMediator
import com.checkout.frames.cvvcomponent.models.CVVComponentConfig
import io.mockk.mockk
import org.amshove.kluent.internal.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class InternalCVVComponentApiTest {
    private lateinit var publicKey: String
    private lateinit var environment: Environment
    private lateinit var context: Context
    private lateinit var cvvComponentConfig: CVVComponentConfig

    @BeforeEach
    fun setUp() {
        // Initialize mock instances for parameters
        publicKey = "test_key"
        environment = Environment.SANDBOX // or any desired environment
        context = mockk()
        cvvComponentConfig = mockk()
    }

    @Test
    fun `when createComponentMediator is invoked then InternalCVVComponentMediator is correctly created`() {
        // Given
        val cvvComponentApi: CVVComponentApi = InternalCVVComponentApi(publicKey, environment, context)

        // When
        val componentMediator = cvvComponentApi.createComponentMediator(cvvComponentConfig)

        // Then
        assertEquals(InternalCVVComponentMediator::class.java, componentMediator.javaClass)
    }
}

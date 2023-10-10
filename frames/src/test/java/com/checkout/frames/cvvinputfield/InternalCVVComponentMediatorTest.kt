package com.checkout.frames.cvvinputfield

import com.checkout.base.model.CardScheme
import com.checkout.base.usecase.UseCase
import com.checkout.frames.cvvinputfield.api.InternalCVVComponentMediator
import com.checkout.frames.cvvinputfield.models.CVVComponentConfig
import com.checkout.frames.cvvinputfield.models.InternalCVVTokenRequest
import com.checkout.tokenization.model.CVVTokenizationResultHandler
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class InternalCVVComponentMediatorTest {
    @MockK
    private lateinit var mockCVVTokenizationUseCase: UseCase<InternalCVVTokenRequest, Unit>

    @MockK
    private lateinit var mockCVVComponentConfig: CVVComponentConfig

    private lateinit var internalCVVComponentMediator: InternalCVVComponentMediator

    @BeforeEach
    fun setUp() {
        every { mockCVVComponentConfig.cardScheme } returns CardScheme.VISA
        every { mockCVVTokenizationUseCase.execute(any()) } returns Unit
        internalCVVComponentMediator = InternalCVVComponentMediator(mockCVVComponentConfig, mockCVVTokenizationUseCase)
    }

    @Test
    fun `when createToken is invoked then CVVTokenizationUseCase is executed with correct request`() {
        // Given
        val inputCVVValue = "123"
        internalCVVComponentMediator.setCVVInputFieldTextValue(inputCVVValue)
        val mockResultHandler: (CVVTokenizationResultHandler) -> Unit = mockk()
        val internalCVVTokenRequest = InternalCVVTokenRequest(inputCVVValue, CardScheme.VISA, mockResultHandler)

        // When
        internalCVVComponentMediator.createToken(mockResultHandler)

        // Then
        verify(exactly = 1) {
            mockCVVTokenizationUseCase.execute(eq(internalCVVTokenRequest))
        }
    }
}

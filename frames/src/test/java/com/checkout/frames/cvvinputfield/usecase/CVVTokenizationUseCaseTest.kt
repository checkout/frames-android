package com.checkout.frames.cvvinputfield.usecase

import com.checkout.api.CheckoutApiService
import com.checkout.base.model.CardScheme
import com.checkout.frames.cvvinputfield.models.InternalCVVTokenRequest
import com.checkout.tokenization.model.CVVTokenizationRequest
import com.checkout.tokenization.model.CVVTokenizationResultHandler
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class CVVTokenizationUseCaseTest {

    @RelaxedMockK
    private lateinit var mockCheckoutApiService: CheckoutApiService

    private lateinit var cvvTokenizationUseCase: CVVTokenizationUseCase

    @BeforeEach
    fun setUp() {
        val mockCVVTokenizationRequest = mockk<CVVTokenizationRequest>()
        every { mockCheckoutApiService.createToken(mockCVVTokenizationRequest) } just runs

        cvvTokenizationUseCase = CVVTokenizationUseCase(mockCheckoutApiService)
    }

    @Test
    fun `execute should call createToken with correct parameters`() {
        // Given
        val cvv = "123"
        val cardScheme = CardScheme.VISA
        val resultHandler: (CVVTokenizationResultHandler) -> Unit = {}

        val requestData = InternalCVVTokenRequest(cvv, cardScheme, resultHandler)

        // When
        cvvTokenizationUseCase.execute(requestData)

        // Then
        val expectedRequest = CVVTokenizationRequest(cvv, cardScheme, resultHandler)
        verify(exactly = 1) { mockCheckoutApiService.createToken(expectedRequest) }
    }
}

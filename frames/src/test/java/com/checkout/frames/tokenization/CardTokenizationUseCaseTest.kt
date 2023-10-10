package com.checkout.frames.tokenization

import android.annotation.SuppressLint
import com.checkout.api.CheckoutApiService
import com.checkout.base.usecase.UseCase
import com.checkout.frames.model.request.InternalCardTokenRequest
import com.checkout.frames.usecase.CardTokenizationUseCase
import com.checkout.tokenization.model.Card
import com.checkout.tokenization.model.CardTokenRequest
import com.checkout.tokenization.model.ExpiryDate
import com.checkout.tokenization.model.TokenDetails
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@SuppressLint("NewApi")
@ExtendWith(MockKExtension::class)
internal class CardTokenizationUseCaseTest {

    @RelaxedMockK
    lateinit var mockCheckoutApiService: CheckoutApiService

    private lateinit var cardTokenizationUseCase: UseCase<InternalCardTokenRequest, Unit>

    private var merchantOnStart: Boolean = false
    private var merchantResultErrorMessage: String? = null
    private var merchantResultTokenDetails: TokenDetails? = null

    @BeforeEach
    fun setUp() {
        cardTokenizationUseCase = CardTokenizationUseCase(
            mockCheckoutApiService,
            { merchantOnStart = true },
            { merchantResultTokenDetails = it },
            { merchantResultErrorMessage = it },
        )
    }

    @Test
    fun `when usecase is executed then onStart is invoked`() {
        // Given
        val fakeCard = Card(ExpiryDate(2, 24), number = "")
        val request = InternalCardTokenRequest(fakeCard, {}, {})
        merchantOnStart = false

        // When
        cardTokenizationUseCase.execute(request)

        // Then
        assertTrue(merchantOnStart)
    }

    @Test
    fun `when usecase is executed then tokenization through checkout api service is requested`() {
        // Given
        val fakeCard = Card(ExpiryDate(2, 24), number = "")
        val request = InternalCardTokenRequest(fakeCard, {}, {})
        val capturedCardTokenRequest = slot<CardTokenRequest>()

        // When
        cardTokenizationUseCase.execute(request)

        // Then
        verify(exactly = 1) { mockCheckoutApiService.createToken(capture(capturedCardTokenRequest)) }
        assertEquals(fakeCard, capturedCardTokenRequest.captured.card)
    }

    @Test
    fun `when usecase is executed and tokenization succeed then both merchant and internal onSuccess callbacks invoked`() {
        // Given
        var internalSuccessCallbackInvoked = false
        var internalFailureCallbackInvoked = false
        val request = InternalCardTokenRequest(
            Card(ExpiryDate(2, 24), number = ""),
            { internalSuccessCallbackInvoked = true },
            { internalFailureCallbackInvoked = true },
        )
        val capturedCardTokenRequest = slot<CardTokenRequest>()
        val expectedTokenDetails = TokenDetails(
            "type", "token_token", "02/23", 2, 23,
            null, "", "", "", null, null, null, null,
            null, null, null, null, null,
        )
        merchantResultTokenDetails = null
        merchantResultErrorMessage = null

        // When
        cardTokenizationUseCase.execute(request)
        verify(exactly = 1) { mockCheckoutApiService.createToken(capture(capturedCardTokenRequest)) }
        capturedCardTokenRequest.captured.onSuccess(expectedTokenDetails)

        // Then
        assertEquals(expectedTokenDetails, merchantResultTokenDetails)
        assertTrue(merchantResultErrorMessage == null)
        assertTrue(internalSuccessCallbackInvoked)
        assertFalse(internalFailureCallbackInvoked)
    }

    @Test
    fun `when usecase is executed and tokenization failed then both merchant and internal onFailure callbacks invoked`() {
        // Given
        var internalSuccessCallbackInvoked = false
        var internalFailureCallbackInvoked = false
        val request = InternalCardTokenRequest(
            Card(ExpiryDate(2, 24), number = ""),
            { internalSuccessCallbackInvoked = true },
            { internalFailureCallbackInvoked = true },
        )
        val capturedCardTokenRequest = slot<CardTokenRequest>()
        val expectedErrorMessage = "Error message. Test."
        merchantResultTokenDetails = null
        merchantResultErrorMessage = null

        // When
        cardTokenizationUseCase.execute(request)
        verify(exactly = 1) { mockCheckoutApiService.createToken(capture(capturedCardTokenRequest)) }
        capturedCardTokenRequest.captured.onFailure(expectedErrorMessage)

        // Then
        assertEquals(expectedErrorMessage, merchantResultErrorMessage)
        assertTrue(merchantResultTokenDetails == null)
        assertTrue(internalFailureCallbackInvoked)
        assertFalse(internalSuccessCallbackInvoked)
    }
}

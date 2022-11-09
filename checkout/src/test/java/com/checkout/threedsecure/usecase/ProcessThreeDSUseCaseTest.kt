package com.checkout.threedsecure.usecase

import com.checkout.base.usecase.UseCase
import com.checkout.threedsecure.error.ThreeDSError
import com.checkout.threedsecure.model.ProcessThreeDSRequest
import com.checkout.threedsecure.model.ThreeDSResult
import io.mockk.MockKAnnotations
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class ProcessThreeDSUseCaseTest {

    private lateinit var processThreeDSUseCase: UseCase<ProcessThreeDSRequest, ThreeDSResult?>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        processThreeDSUseCase = ProcessThreeDSUseCase()
    }

    @Test
    fun `when redirect url is recognized as success then return success result with payment token`() {
        // Given
        val mockRequest = ProcessThreeDSRequest(
            redirectUrl = "https://test.com/success?cko-payment-token=paymentToken&cko-session-id=sessionId",
            successUrl = "https://test.com/success",
            failureUrl = "https://test.com/failure"
        )

        // When
        val result = processThreeDSUseCase.execute(mockRequest)

        // Then
        assertTrue(result is ThreeDSResult.Success)
        assertEquals("paymentToken", (result as? ThreeDSResult.Success)?.token)
    }

    @Test
    fun `when redirect url is recognized as success then return success result with sessionId`() {
        // Given
        val mockRequest = ProcessThreeDSRequest(
            redirectUrl = "https://www.successUrl.com/test?cko-session-id=sessionId",
            successUrl = "https://www.successUrl.com/test",
            failureUrl = "https://www.failure.com"
        )

        // When
        val result = processThreeDSUseCase.execute(mockRequest)

        // Then
        assertTrue(result is ThreeDSResult.Success)
        assertEquals("sessionId", (result as? ThreeDSResult.Success)?.token)
    }

    @Test
    fun `when redirect url is recognized as success but without token or id then return correct error result`() {
        // Given
        val mockRequest = ProcessThreeDSRequest(
            redirectUrl = "https://successUrl/test",
            successUrl = "https://successUrl/test",
            failureUrl = "https://failure/test"
        )
        val expected = ThreeDSResult.Error(
            ThreeDSError(ThreeDSError.COULD_NOT_EXTRACT_TOKEN, "Url can't be null.")
        )

        // When
        val result = processThreeDSUseCase.execute(mockRequest)

        // Then
        assertTrue(result is ThreeDSResult.Error)
        assertEquals(expected.error.errorCode, (result as? ThreeDSResult.Error)?.error?.errorCode)
    }

    @Test
    fun `when redirect url is recognized as failure then return failure result`() {
        // Given
        val mockRequest = ProcessThreeDSRequest(
            redirectUrl = "https://failure/test",
            successUrl = "https://successUrl/test",
            failureUrl = "https://failure/test"
        )

        // When
        val result = processThreeDSUseCase.execute(mockRequest)

        // Then
        assertTrue(result is ThreeDSResult.Failure)
    }

    @Test
    fun `when redirect url is not recognized then return null`() {
        // Given
        val mockRequest = ProcessThreeDSRequest(
            redirectUrl = "https://www.test.for.test/unknown",
            successUrl = "https://successUrl/test",
            failureUrl = "https://failure/test"
        )

        // When
        val result = processThreeDSUseCase.execute(mockRequest)

        // Then
        assertEquals(null, result)
    }
}

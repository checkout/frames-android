package com.checkout.threedsecure

import android.content.Context
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.checkout.base.usecase.UseCase
import com.checkout.threedsecure.error.ThreeDSError
import com.checkout.threedsecure.logging.ThreeDSLogger
import com.checkout.threedsecure.model.ProcessThreeDSRequest
import com.checkout.threedsecure.model.ThreeDSRequest
import com.checkout.threedsecure.model.ThreeDSResult
import com.checkout.threedsecure.model.ThreeDSResultHandler
import com.checkout.threedsecure.webview.ThreeDSWebView
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.slot
import io.mockk.spyk
import io.mockk.verify
import org.amshove.kluent.internal.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@ExtendWith(MockKExtension::class)
internal class ThreeDSExecutorTest {

    @RelaxedMockK
    lateinit var processThreeDSUseCase: UseCase<ProcessThreeDSRequest, ThreeDSResult?>

    @RelaxedMockK
    lateinit var mockContext: Context

    @RelaxedMockK
    lateinit var mockContainer: ViewGroup

    @RelaxedMockK
    lateinit var mockThreeDSWebView: ThreeDSWebView

    @RelaxedMockK
    lateinit var mockLogger: ThreeDSLogger

    private lateinit var executor: ThreeDSExecutor

    @BeforeEach
    fun setUp() {
        executor = spyk(ThreeDSExecutor(processThreeDSUseCase, mockLogger))

        every { mockContainer.context } returns mockContext
        every { executor.provideWebView(any()) } returns mockThreeDSWebView
    }

    @Test
    fun `when execute is invoked then ThreeDSWebView is added to the container`() {
        // Given
        val mockRequest = ThreeDSRequest(
            mockContainer,
            challengeUrl = "https://www.test.com",
            successUrl = "https://success",
            failureUrl = "https://failure",
            resultHandler = {},
        )

        // When
        executor.execute(mockRequest)

        // Then
        verify { mockContainer.addView(any<ThreeDSWebView>()) }
    }

    @Test
    fun `when handle result is invoked then handle 3DS is requested and result passed to handler`() {
        // Given
        val mockUrl = "https://www.test.com"
        val mockSuccessUrl = "https://www.test.com/Success"
        val mockFailureUrl = "https://www.test.com/Failure"
        val mockResult = ThreeDSResult.Failure
        val mockResultHandler = mockk<ThreeDSResultHandler>()
        val capturedRequest = slot<ProcessThreeDSRequest>()

        every { mockResultHandler.invoke(any()) } returns Unit
        every { processThreeDSUseCase.execute(any()) } returns mockResult

        // When
        executor.handleResult(mockUrl, mockSuccessUrl, mockFailureUrl, mockResultHandler)

        // Then
        verify { processThreeDSUseCase.execute(capture(capturedRequest)) }
        verify { mockResultHandler.invoke(mockResult) }
        with(capturedRequest.captured) {
            assertEquals(mockUrl, redirectUrl)
            assertEquals(mockSuccessUrl, successUrl)
            assertEquals(mockFailureUrl, failureUrl)
        }
    }

    @ParameterizedTest(
        name = "when handle result is invoked with {0} " +
            "then complete event with success = {1}, error = {2} and token {3} is logged",
    )
    @MethodSource("resultArguments")
    fun `when handle result is invoked then complete event with correct data is logged`(
        result: ThreeDSResult,
        success: Boolean,
    ) {
        // Given
        val mockUrl = "https://www.test.com"
        val mockSuccessUrl = "https://www.test.com/Success"
        val mockFailureUrl = "https://www.test.com/Failure"
        val mockResultHandler = mockk<ThreeDSResultHandler>()
        val capturedToken = mutableListOf<String?>()
        val capturedError = mutableListOf<Throwable?>()

        every { mockResultHandler.invoke(any()) } returns Unit
        every { processThreeDSUseCase.execute(any()) } returns result

        // When
        executor.handleResult(mockUrl, mockSuccessUrl, mockFailureUrl, mockResultHandler)

        // Then
        verify { mockLogger.logCompletedEvent(success, captureNullable(capturedToken), captureNullable(capturedError)) }
        when (result) {
            is ThreeDSResult.Success -> {
                assertEquals(result.token, capturedToken.firstOrNull())
                assertEquals(null, capturedError.firstOrNull())
            }

            is ThreeDSResult.Failure -> {
                assertEquals(null, capturedToken.firstOrNull())
                assertEquals(null, capturedError.firstOrNull())
            }

            is ThreeDSResult.Error -> {
                assertEquals(null, capturedToken.firstOrNull())
                assertEquals(result.error.errorCode, (capturedError.firstOrNull() as? ThreeDSError)?.errorCode)
            }
        }
    }

    @Test
    fun `when handle error is invoked then loaded event with correct data is logged`() {
        // Given
        val mockError = ThreeDSError("1111", "test error")
        val mockResultHandler = mockk<ThreeDSResultHandler>()
        val capturedError = mutableListOf<Throwable?>()

        every { mockResultHandler.invoke(any()) } returns Unit

        // When
        executor.handleError(mockError, mockResultHandler)

        // Then
        verify { mockLogger.logLoadedEvent(false, captureNullable(capturedError)) }
        (capturedError.firstOrNull() as? ThreeDSError)?.let {
            assertEquals(mockError.errorCode, it.errorCode)
            assertEquals(mockError.message, it.message)
        } ?: throw ClassCastException("Captured error should be ThreeDSError")
    }

    companion object {
        @RequiresApi(Build.VERSION_CODES.N)
        @JvmStatic
        fun resultArguments(): Stream<Arguments> = Stream.of(
            Arguments.of(ThreeDSResult.Success("token"), true),
            Arguments.of(ThreeDSResult.Error(ThreeDSError("123", "test")), false),
            Arguments.of(ThreeDSResult.Failure, false),
        )
    }
}

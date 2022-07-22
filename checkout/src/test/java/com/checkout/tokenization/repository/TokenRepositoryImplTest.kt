package com.checkout.tokenization.repository

import com.checkout.CardValidatorFactory
import com.checkout.mock.CardTokenTestData
import com.checkout.network.response.NetworkApiResponse
import com.checkout.tokenization.TokenNetworkApiClient
import com.checkout.tokenization.error.TokenizationError
import com.checkout.tokenization.mapper.request.AddressToAddressValidationRequestDataMapper
import com.checkout.tokenization.mapper.request.CardToTokenRequestMapper
import com.checkout.tokenization.mapper.response.CardTokenizationNetworkDataMapper
import com.checkout.tokenization.model.CardTokenRequest
import com.checkout.tokenization.model.GooglePayTokenRequest
import com.checkout.tokenization.response.TokenDetailsResponse
import com.checkout.tokenization.usecase.ValidateTokenizationDataUseCase
import com.checkout.validation.validator.AddressValidator
import com.checkout.validation.validator.PhoneValidator
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
@OptIn(ExperimentalCoroutinesApi::class)
internal class TokenRepositoryImplTest {
    @RelaxedMockK
    private lateinit var mockTokenNetworkApiClient: TokenNetworkApiClient

    private lateinit var tokenRepositoryImpl: TokenRepositoryImpl

    @BeforeEach
    fun setUp() {
        tokenRepositoryImpl = TokenRepositoryImpl(
            mockTokenNetworkApiClient,
            CardToTokenRequestMapper(),
            CardTokenizationNetworkDataMapper(),
            ValidateTokenizationDataUseCase(
                CardValidatorFactory.createInternal(),
                AddressValidator(),
                PhoneValidator(),
                AddressToAddressValidationRequestDataMapper()
            )
        )
    }

    @DisplayName("CardToken Details invocation")
    @Nested
    inner class GetCardTokenDetails {
        @Test
        fun `when sendCardTokenRequest invoked with success response then success handler invoked`() {
            testCardTokenResultInvocation(
                true,
                NetworkApiResponse.Success(CardTokenTestData.tokenDetailsResponse())
            )
        }

        @Test
        fun `when sendCardTokenRequest invoked with network error response then failure handler invoked`() {
            testCardTokenResultInvocation(
                false,
                NetworkApiResponse.NetworkError(NullPointerException())
            )
        }

        @Test
        fun `when sendCardTokenRequest invoked with server error response then failure handler invoked`() {
            testCardTokenResultInvocation(
                false,
                NetworkApiResponse.ServerError(null, 123)
            )
        }

        @Test
        fun `when sendCardTokenRequest invoked with internal error response then failure handler invoked`() {
            testCardTokenResultInvocation(
                false,
                NetworkApiResponse.InternalError(
                    TokenizationError(
                        "dummy code",
                        "exception.message",
                        null
                    )
                )
            )
        }

        private fun testCardTokenResultInvocation(
            successHandlerInvoked: Boolean,
            response: NetworkApiResponse<TokenDetailsResponse>
        ) =
            runTest {
                // Given
                var isSuccess = false

                val testDispatcher = UnconfinedTestDispatcher(testScheduler)
                Dispatchers.setMain(testDispatcher)

                tokenRepositoryImpl.networkCoroutineScope = CoroutineScope(StandardTestDispatcher(testScheduler))

                coEvery { mockTokenNetworkApiClient.sendCardTokenRequest(any()) } returns response

                // When
                tokenRepositoryImpl.sendCardTokenRequest(
                    CardTokenRequest(
                        CardTokenTestData.card,
                        onSuccess = { isSuccess = true },
                        onFailure = { isSuccess = false }
                    )
                )

                // Then
                launch {
                    if (successHandlerInvoked) assertTrue(isSuccess)
                    else assertFalse(isSuccess)
                }
            }
    }

    @DisplayName("GooglePayToken Details invocation")
    @Nested
    inner class GetGooglePayTokenNetworkRequestDetails {
        @Test
        fun `when sendGooglePayTokenRequest invoked with success response then success handler invoked`() {
            testGooglePayTokenResultInvocation(
                true,
                NetworkApiResponse.Success(CardTokenTestData.tokenDetailsResponse())
            )
        }

        @Test
        fun `when sendGooglePayTokenRequest invoked with network error response then failure handler invoked`() {
            testGooglePayTokenResultInvocation(
                false,
                NetworkApiResponse.NetworkError(Exception("Network connection lost"))
            )
        }

        @Test
        fun `when sendGooglePayTokenRequest invoked with server error response then failure handler invoked`() {
            testGooglePayTokenResultInvocation(
                false,
                NetworkApiResponse.ServerError(null, 123)
            )
        }

        @Test
        fun `when sendGooglePayTokenRequest invoked with internal error response then failure handler invoked`() {
            testGooglePayTokenResultInvocation(
                false,
                NetworkApiResponse.InternalError(
                    TokenizationError(
                        TokenizationError.GOOGLE_PAY_REQUEST_PARSING_ERROR,
                        "exception.message",
                        null
                    )
                )
            )
        }

        @Test
        fun `when sendGooglePayTokenRequest error invoked with invalid jsonpayload request then throw custom TokenizationError`() {
            testGooglePayErrorHandlerInvocation(
                NetworkApiResponse.InternalError(
                    TokenizationError(
                        TokenizationError.GOOGLE_PAY_REQUEST_PARSING_ERROR,
                        "JSONObject[\"protocolVersion\"] not found.",
                        null
                    )
                )
            )
        }

        private fun testGooglePayTokenResultInvocation(
            successHandlerInvoked: Boolean,
            response: NetworkApiResponse<TokenDetailsResponse>
        ) =
            runTest {
                // Given
                var isSuccess: Boolean? = null

                val testDispatcher = UnconfinedTestDispatcher(testScheduler)
                Dispatchers.setMain(testDispatcher)

                tokenRepositoryImpl.networkCoroutineScope = CoroutineScope(StandardTestDispatcher(testScheduler))

                coEvery { mockTokenNetworkApiClient.sendGooglePayTokenRequest(any()) } returns response

                // When
                tokenRepositoryImpl.sendGooglePayTokenRequest(
                    GooglePayTokenRequest(
                        "{protocolVersion: ECv1,signature: “test”,signedMessage: testSignedMessage}",
                        onSuccess = { isSuccess = true },
                        onFailure = { isSuccess = false }
                    )
                )

                // Then
                launch {
                    assertEquals(isSuccess.toString(), successHandlerInvoked.toString())
                }
            }

        private fun testGooglePayErrorHandlerInvocation(
            response: NetworkApiResponse<TokenDetailsResponse>
        ) = runTest {
            // Given
            var isSuccess: Boolean? = null
            var errorMessage = ""
            val successHandlerInvoked = false

            val testDispatcher = UnconfinedTestDispatcher(testScheduler)
            Dispatchers.setMain(testDispatcher)

            tokenRepositoryImpl.networkCoroutineScope = CoroutineScope(StandardTestDispatcher(testScheduler))

            coEvery { mockTokenNetworkApiClient.sendGooglePayTokenRequest(any()) } returns response

            // When
            tokenRepositoryImpl.sendGooglePayTokenRequest(
                GooglePayTokenRequest(
                    "{test: ECv1,signature: “testSignature”,signedMessage: testSignedMessage}",
                    onSuccess = { isSuccess = true },
                    onFailure = {
                        isSuccess = false
                        errorMessage = it
                    }
                )
            )

            // Then
            launch {
                assertEquals(isSuccess.toString(), successHandlerInvoked.toString())
                assertEquals(errorMessage, (response as? NetworkApiResponse.InternalError)?.throwable?.message ?: "_")
            }
        }
    }
}

package com.checkout.tokenization

import android.os.Build
import androidx.annotation.RequiresApi
import com.checkout.mock.GetTokenDetailsResponseTestJson
import com.checkout.mock.TokenizationRequestTestData
import com.checkout.network.OkHttpProvider
import com.checkout.network.response.NetworkApiResponse
import com.checkout.tokenization.entity.GooglePayEntity
import com.checkout.tokenization.mapper.request.CVVToTokenNetworkRequestMapper
import com.checkout.tokenization.mapper.request.CardToTokenRequestMapper
import com.checkout.tokenization.request.GooglePayTokenNetworkRequest
import com.checkout.tokenization.utils.TokenizationConstants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBeNull
import org.amshove.kluent.shouldNotBeNullOrEmpty
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@OptIn(ExperimentalCoroutinesApi::class)
internal class TokenNetworkApiClientTest {
    private lateinit var mockTokenApiServer: MockWebServer

    private lateinit var tokenNetworkApiClient: TokenNetworkApiClient

    private lateinit var moshiClient: Moshi

    @BeforeEach
    fun setUp() {
        mockTokenApiServer = MockWebServer()
        mockTokenApiServer.start()

        val serverBaseUrl = mockTokenApiServer.url("/")
            .toString()

        moshiClient =
            Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()

        tokenNetworkApiClient = TokenNetworkApiClient(
            serverBaseUrl,
            OkHttpProvider.createOkHttpClient("test_key"),
            moshiClient,
        )
    }

    fun enqueueMockResponse(code: Int, json: String? = null) {
        val mockResponse = MockResponse().apply {
            setResponseCode(code)
            if (json != null) {
                setBody(json)
            }
        }
        mockTokenApiServer.enqueue(mockResponse)
    }

    fun enqueueNoResponse() {
        val mockResponse = MockResponse().apply {
            setSocketPolicy(SocketPolicy.NO_RESPONSE)
        }
        mockTokenApiServer.enqueue(mockResponse)
    }

    @AfterEach
    fun tearDown() {
        mockTokenApiServer.shutdown()
    }

    @DisplayName("Get CardToken Details")
    @Nested
    inner class GetCardTokenDetails {
        @DisplayName("card token details: The information is extracted from the response")
        @Test
        fun informationExtractedFromResponse() = runTest {
            // Given
            enqueueMockResponse(
                200,
                GetTokenDetailsResponseTestJson.cardTokenDetailsResponse,
            )

            // When
            val response = tokenNetworkApiClient.sendCardTokenRequest(
                CardToTokenRequestMapper().map(TokenizationRequestTestData.card),
            )

            launch {
                // Then
                assertTrue(response is NetworkApiResponse.Success)
                with((response as NetworkApiResponse.Success).body) {
                    type.shouldBeEqualTo("card")
                    token.shouldBeEqualTo("tok_ubfj2q76miwundwlk72vxt2i7q")
                    expiresOn.shouldBeEqualTo("2019-08-24T14:15:22Z")
                    expiryMonth.shouldBeEqualTo(6)
                    expiryYear.shouldBeEqualTo(2025)
                    scheme.shouldBeEqualTo("VISA")
                    last4.shouldBeEqualTo("9996")
                    bin.shouldBeEqualTo("454347")
                    cardType.shouldBeEqualTo("Credit")
                    cardCategory.shouldBeEqualTo("Consumer")
                    issuer.shouldBeEqualTo("GOTHAM STATE BANK")
                    issuerCountry.shouldBeEqualTo("US")
                    productId.shouldBeEqualTo("F")
                    productType.shouldBeEqualTo("CLASSIC")
                    billingAddress.shouldBeEqualTo(TokenizationRequestTestData.addressEntity)
                    phone.shouldBeEqualTo(TokenizationRequestTestData.phoneEntity)
                    name.shouldBeEqualTo("Bruce Wayne")
                }

                assertFalse(response is NetworkApiResponse.ServerError)

                assertFalse(response is NetworkApiResponse.NetworkError)

                assertFalse(response is NetworkApiResponse.InternalError)
            }
        }

        @DisplayName("Get CardToken Details API failed")
        @ParameterizedTest(name = "{index}: The token API responds with an error for errorCode:{0}")
        @ValueSource(ints = [401, 422, 502, 404])
        fun apiRespondsWithError(errorCode: Int) = runTest {
            // Given
            enqueueMockResponse(
                errorCode,
                GetTokenDetailsResponseTestJson.cardTokenDetailsErrorResponse,
            )

            // When
            val response = tokenNetworkApiClient.sendCardTokenRequest(
                CardToTokenRequestMapper().map(TokenizationRequestTestData.card),
            )

            // Then
            launch {
                // Then
                assertFalse(response is NetworkApiResponse.Success)

                assertTrue(response is NetworkApiResponse.ServerError)
                with((response as NetworkApiResponse.ServerError).body) {
                    this?.requestId.shouldNotBeNull()
                    this?.errorCodes.shouldNotBeNull()
                    this?.errorType.shouldNotBeNull()
                }

                assertFalse(response is NetworkApiResponse.NetworkError)

                assertFalse(response is NetworkApiResponse.InternalError)
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        @Test
        @DisplayName("Get CardToken Details API does not respond and throw network error")
        fun apiDoesNotRespond() = runTest {
            // Given
            enqueueNoResponse()

            // When
            val response = tokenNetworkApiClient.sendCardTokenRequest(
                CardToTokenRequestMapper().map(TokenizationRequestTestData.card),
            )

            launch {
                // Then
                assertFalse(response is NetworkApiResponse.Success)

                assertFalse(response is NetworkApiResponse.ServerError)

                assertTrue(response is NetworkApiResponse.NetworkError)
                with((response as NetworkApiResponse.NetworkError)) {
                    throwable.shouldNotBeNull()
                }

                assertFalse(response is NetworkApiResponse.InternalError)
            }
        }
    }

    @DisplayName("Get CVVToken Details")
    @Nested
    inner class GetCVVTokenDetails {
        @DisplayName("cvv token details: The information is extracted from the response")
        @Test
        fun informationExtractedFromResponse() = runTest {
            // Given
            enqueueMockResponse(
                200,
                GetTokenDetailsResponseTestJson.cvvTokenDetailsResponse,
            )

            // When
            val response = tokenNetworkApiClient.sendCVVTokenRequest(
                CVVToTokenNetworkRequestMapper().map(TokenizationRequestTestData.cvvTokenizationRequest),
            )

            launch {
                // Then
                assertTrue(response is NetworkApiResponse.Success)
                with((response as NetworkApiResponse.Success).body) {
                    type.shouldBeEqualTo("cvv")
                    token.shouldBeEqualTo("tok_ubfj2q76miwundwlk72vxt2i7q")
                    expiresOn.shouldBeEqualTo("2019-08-24T14:15:22Z")
                }

                assertFalse(response is NetworkApiResponse.ServerError)

                assertFalse(response is NetworkApiResponse.NetworkError)

                assertFalse(response is NetworkApiResponse.InternalError)
            }
        }

        @DisplayName("Get CVVToken Details API failed")
        @ParameterizedTest(name = "{index}: The token API responds with an error for errorCode:{0}")
        @ValueSource(ints = [401, 422, 502, 404])
        fun apiRespondsWithError(errorCode: Int) = runTest {
            // Given
            enqueueMockResponse(
                code = errorCode,
                json = GetTokenDetailsResponseTestJson.cvvTokenDetailsErrorResponse,
            )

            // When
            val response = tokenNetworkApiClient.sendCVVTokenRequest(
                CVVToTokenNetworkRequestMapper().map(TokenizationRequestTestData.cvvTokenizationRequest),
            )

            // Then
            launch {
                // Then
                assertFalse(response is NetworkApiResponse.Success)

                assertTrue(response is NetworkApiResponse.ServerError)
                with((response as NetworkApiResponse.ServerError).body) {
                    this?.requestId.shouldNotBeNull()
                    this?.errorCodes.shouldNotBeNull()
                    this?.errorType.shouldNotBeNull()
                }

                assertFalse(response is NetworkApiResponse.NetworkError)

                assertFalse(response is NetworkApiResponse.InternalError)
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        @Test
        @DisplayName("Get CVVToken Details API does not respond and throw network error")
        fun apiDoesNotRespond() = runTest {
            // Given
            enqueueNoResponse()

            // When
            val response = tokenNetworkApiClient.sendCVVTokenRequest(
                CVVToTokenNetworkRequestMapper().map(TokenizationRequestTestData.cvvTokenizationRequest),
            )

            launch {
                // Then
                assertFalse(response is NetworkApiResponse.Success)

                assertFalse(response is NetworkApiResponse.ServerError)

                assertTrue(response is NetworkApiResponse.NetworkError)
                with((response as NetworkApiResponse.NetworkError)) {
                    throwable.shouldNotBeNull()
                }

                assertFalse(response is NetworkApiResponse.InternalError)
            }
        }
    }

    @DisplayName("Get GooglePayToken Details")
    @Nested
    inner class GetGooglePayTokenNetworkRequestDetails {
        @DisplayName("googlePay token details: The information is extracted from the response")
        @Test
        fun informationExtractedFromResponse() = runTest {
            // Given
            enqueueMockResponse(
                200,
                GetTokenDetailsResponseTestJson.googlePayTokenDetailsResponse,
            )

            // When
            val response =
                tokenNetworkApiClient.sendGooglePayTokenRequest(
                    GooglePayTokenNetworkRequest(
                        TokenizationConstants.GOOGLE_PAY,
                        GooglePayEntity(),
                    ),
                )

            // Then
            when (response) {
                is NetworkApiResponse.Success -> {
                    response.body.type.shouldBeEqualTo("googlepay")
                    response.body.token.shouldBeEqualTo("tok_ubfj2q76miwundwlk72vxt2i7q")
                    response.body.expiresOn.shouldBeEqualTo("2019-08-24T14:15:22Z")
                    response.body.expiryMonth.shouldBeEqualTo(6)
                    response.body.expiryYear.shouldBeEqualTo(2025)
                    response.body.scheme.shouldBeEqualTo("VISA")
                    response.body.last4.shouldBeEqualTo("9996")
                    response.body.bin.shouldBeEqualTo("454347")
                    response.body.cardType.shouldBeEqualTo("Credit")
                    response.body.cardCategory.shouldBeEqualTo("Consumer")
                    response.body.issuer.shouldBeEqualTo("GOTHAM STATE BANK")
                    response.body.issuerCountry.shouldBeEqualTo("US")
                    response.body.productId.shouldBeEqualTo("F")
                    response.body.productType.shouldBeEqualTo("CLASSIC")
                }

                is NetworkApiResponse.NetworkError -> {
                    org.amshove.kluent.fail(
                        "Get googlePayToken details should be successful:" +
                            " ${response.throwable.message}",
                    )
                }

                is NetworkApiResponse.ServerError -> {
                    org.amshove.kluent.fail(
                        "Get googlePayToken details should be successful: " +
                            "${response.body?.errorCodes}",
                    )
                }

                is NetworkApiResponse.InternalError -> {
                    response.throwable.cause?.message.shouldNotBeNullOrEmpty()
                }
            }
        }

        @DisplayName("Get GooglePayToken Details API failed")
        @ParameterizedTest(name = "{index}: The token API responds with an error for errorCode:{0}")
        @ValueSource(ints = [401, 422, 502, 404])
        fun apiRespondsWithError(errorCode: Int) = runTest {
            // Given
            enqueueMockResponse(
                errorCode,
                GetTokenDetailsResponseTestJson.googlePayTokenDetailsErrorResponse,
            )

            // When
            val response = tokenNetworkApiClient.sendGooglePayTokenRequest(
                GooglePayTokenNetworkRequest(
                    TokenizationConstants.GOOGLE_PAY,
                    GooglePayEntity(),
                ),
            )

            // Then
            launch {
                // Then
                assertFalse(response is NetworkApiResponse.Success)

                assertTrue(response is NetworkApiResponse.ServerError)
                with((response as NetworkApiResponse.ServerError).body) {
                    this?.requestId.shouldNotBeNull()
                    this?.errorCodes.shouldNotBeNull()
                    this?.errorType.shouldNotBeNull()
                }

                assertFalse(response is NetworkApiResponse.NetworkError)

                assertFalse(response is NetworkApiResponse.InternalError)
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        @Test
        @DisplayName("Get GooglePayToken Details API does not respond and throw network error")
        fun apiDoesNotRespond() = runTest {
            // Given
            enqueueNoResponse()

            // When
            val response = tokenNetworkApiClient.sendGooglePayTokenRequest(
                GooglePayTokenNetworkRequest(
                    TokenizationConstants.GOOGLE_PAY,
                    GooglePayEntity(),
                ),
            )

            launch {
                // Then
                assertFalse(response is NetworkApiResponse.Success)

                assertFalse(response is NetworkApiResponse.ServerError)

                assertTrue(response is NetworkApiResponse.NetworkError)
                with((response as NetworkApiResponse.NetworkError)) {
                    throwable.shouldNotBeNull()
                }

                assertFalse(response is NetworkApiResponse.InternalError)
            }
        }
    }
}

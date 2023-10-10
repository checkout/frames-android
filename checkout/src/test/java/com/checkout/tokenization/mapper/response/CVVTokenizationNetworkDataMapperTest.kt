package com.checkout.tokenization.mapper.response

import com.checkout.mock.TokenizationRequestTestData
import com.checkout.network.response.NetworkApiResponse
import com.checkout.tokenization.model.CVVTokenDetails
import com.checkout.tokenization.model.CVVTokenizationRequest
import com.checkout.tokenization.model.TokenResult
import com.checkout.tokenization.response.GetCVVTokenDetailsResponse
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should be instance of`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class CVVTokenizationNetworkDataMapperTest {
    private lateinit var cvvTokenizationNetworkDataMapper: CVVTokenizationNetworkDataMapper

    @BeforeEach
    fun setUp() {
        cvvTokenizationNetworkDataMapper = CVVTokenizationNetworkDataMapper()
    }

    @DisplayName("Mapping successful GetCVVTokenDetailsResponse to TokenResult")
    @Nested
    inner class MapToSuccessfulTokenResult {

        @MockK
        private lateinit var mockGetCVVTokenDetailsResponse: GetCVVTokenDetailsResponse

        @Test
        fun `map GetCVVTokenDetailsResponse to TokenResult`() {
            // Given
            val expectedResult = expectedCVVTokenDetails()
            setupMockResponses(expectedResult)

            // When
            val mappedResult =
                cvvTokenizationNetworkDataMapper.toTokenResult(
                    NetworkApiResponse.Success(mockGetCVVTokenDetailsResponse)
                )

            // Then
            mappedResult `should be instance of` TokenResult.Success::class

            val actualResult = (mappedResult as TokenResult.Success).result

            actualResult `should be equal to` expectedResult
        }

        @Test
        fun `unexpected response type is a failure`() {
            // Given
            val unexpectedResponseObject = TokenizationRequestTestData.cvvTokenizationRequest

            // When
            val mappedResult =
                cvvTokenizationNetworkDataMapper.toTokenResult(NetworkApiResponse.Success(unexpectedResponseObject))

            // Then
            mappedResult `should be instance of` TokenResult.Failure::class

            (mappedResult as TokenResult.Failure).error.message `should be equal to`
                    "${CVVTokenizationRequest::class.java.name} cannot be mapped to a CVVTokenDetails"
        }

        private fun expectedCVVTokenDetails(): CVVTokenDetails = CVVTokenDetails(
            type = "CVV", token = "tok_test", expiresOn = "2019-08-24T14:15:22Z"
        )

        private fun setupMockResponses(
            tokenDetails: CVVTokenDetails,
        ) {
            every { mockGetCVVTokenDetailsResponse.type } returns tokenDetails.type
            every { mockGetCVVTokenDetailsResponse.token } returns tokenDetails.token
            every { mockGetCVVTokenDetailsResponse.expiresOn } returns tokenDetails.expiresOn
        }
    }

    @DisplayName("Mapping unsuccessful NetworkApiResponse")
    @Nested
    inner class MapToFailedTokenResult {

        @MockK
        private lateinit var mockServerErrorResponse: NetworkApiResponse.ServerError

        @MockK
        private lateinit var mockNetworkErrorResponse: NetworkApiResponse.NetworkError

        @Test
        fun `map ServerError response to TokenResult`() {
            // Given
            every { mockServerErrorResponse.code } returns 400
            every { mockServerErrorResponse.body } returns TokenizationRequestTestData.errorResponse()

            // When
            val mappedResult = cvvTokenizationNetworkDataMapper.toTokenResult(mockServerErrorResponse)

            // Then
            mappedResult `should be instance of` TokenResult.Failure::class
            (mappedResult as TokenResult.Failure).error.message `should be equal to`
                    "Token request failed - testErrorType (HttpStatus: 400)"
        }

        @Test
        fun `map NetworkError response to TokenResult`() {
            // Given
            val cause = Throwable("test exception")
            every { mockNetworkErrorResponse.throwable } returns cause

            // When
            val mappedResult = cvvTokenizationNetworkDataMapper.toTokenResult(mockNetworkErrorResponse)

            // Then
            mappedResult `should be instance of` TokenResult.Failure::class
            (mappedResult as TokenResult.Failure).error.cause `should be equal to` cause
        }
    }
}

package com.checkout.tokenization.mapper

import com.checkout.mock.TokenizationRequestTestData
import com.checkout.network.response.NetworkApiResponse
import com.checkout.tokenization.mapper.request.AddressToAddressEntityDataMapper
import com.checkout.tokenization.mapper.request.PhoneToPhoneEntityDataMapper
import com.checkout.tokenization.mapper.response.AddressEntityToAddressDataMapper
import com.checkout.tokenization.mapper.response.CardTokenizationNetworkDataMapper
import com.checkout.tokenization.mapper.response.PhoneEntityToPhoneDataMapper
import com.checkout.tokenization.model.Card
import com.checkout.tokenization.model.TokenDetails
import com.checkout.tokenization.model.TokenResult
import com.checkout.tokenization.response.GetTokenDetailsResponse
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
internal class CardTokenizationNetworkDataMapperTest {

    private lateinit var cardTokenizationNetworkDataMapper: CardTokenizationNetworkDataMapper

    @BeforeEach
    fun setUp() {
        cardTokenizationNetworkDataMapper = CardTokenizationNetworkDataMapper()
    }

    @DisplayName("Mapping successful GetTokenDetailsResponse to TokenResult")
    @Nested
    inner class MapToSuccessfulTokenResult {

        @MockK
        private lateinit var mockGetTokenDetailsResponse: GetTokenDetailsResponse

        @Test
        fun `map GetTokenDetailsResponse to TokenResult`() {
            // Given
            val expectedResult = expectedTokenDetails()
            setupMockResponses(
                expectedResult
            )

            // When
            val mappedResult =
                cardTokenizationNetworkDataMapper.toTokenResult(NetworkApiResponse.Success(mockGetTokenDetailsResponse))

            // Then
            mappedResult `should be instance of` TokenResult.Success::class

            val actualResult = (mappedResult as TokenResult.Success).result

            actualResult `should be equal to` expectedResult
        }

        @Test
        fun `unexpected response type is a failure`() {
            // Given
            val unexpectedResponseObject = TokenizationRequestTestData.card

            // When
            val mappedResult =
                cardTokenizationNetworkDataMapper.toTokenResult(NetworkApiResponse.Success(unexpectedResponseObject))

            // Then
            mappedResult `should be instance of` TokenResult.Failure::class

            (mappedResult as TokenResult.Failure).error.message `should be equal to`
                    "${Card::class.java.name} cannot be mapped to a TokenDetails"
        }

        private fun expectedTokenDetails(): TokenDetails =
            TokenDetails(
                type = "card",
                token = "tok_test",
                expiresOn = "2019-08-24T14:15:22Z",
                expiryMonth = 6,
                expiryYear = 2025,
                scheme = "VISA",
                schemeLocal = "cartes_bancaires",
                last4 = "4242",
                bin = "454347",
                cardType = "Credit",
                cardCategory = "Consumer",
                issuer = "GOTHAM STATE BANK",
                issuerCountry = "US",
                productId = "F",
                productType = "CLASSIC",
                billingAddress = AddressEntityToAddressDataMapper().map(TokenizationRequestTestData.addressEntity),
                phone = PhoneEntityToPhoneDataMapper().map(
                    from = TokenizationRequestTestData.phoneEntity to TokenizationRequestTestData.addressEntity.country
                ),
                name = "Bruce Wayne"
            )

        private fun setupMockResponses(
            tokenDetails: TokenDetails
        ) {
            every { mockGetTokenDetailsResponse.type } returns tokenDetails.type
            every { mockGetTokenDetailsResponse.token } returns tokenDetails.token
            every { mockGetTokenDetailsResponse.scheme } returns tokenDetails.scheme
            every { mockGetTokenDetailsResponse.schemeLocal } returns tokenDetails.schemeLocal
            every { mockGetTokenDetailsResponse.expiresOn } returns tokenDetails.expiresOn
            every { mockGetTokenDetailsResponse.expiryMonth } returns tokenDetails.expiryMonth
            every { mockGetTokenDetailsResponse.expiryYear } returns tokenDetails.expiryYear
            every { mockGetTokenDetailsResponse.last4 } returns tokenDetails.last4
            every { mockGetTokenDetailsResponse.bin } returns tokenDetails.bin
            every { mockGetTokenDetailsResponse.cardType } returns tokenDetails.cardType
            every { mockGetTokenDetailsResponse.cardCategory } returns tokenDetails.cardCategory
            every { mockGetTokenDetailsResponse.issuer } returns tokenDetails.issuer
            every { mockGetTokenDetailsResponse.issuerCountry } returns tokenDetails.issuerCountry
            every { mockGetTokenDetailsResponse.productId } returns tokenDetails.productId
            every { mockGetTokenDetailsResponse.productType } returns tokenDetails.productType
            every { mockGetTokenDetailsResponse.billingAddress } returns tokenDetails.billingAddress?.let {
                AddressToAddressEntityDataMapper().map(
                    it
                )
            }
            every { mockGetTokenDetailsResponse.phone } returns tokenDetails.phone?.let {
                PhoneToPhoneEntityDataMapper().map(
                    it
                )
            }
            every { mockGetTokenDetailsResponse.name } returns tokenDetails.name
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
            val mappedResult = cardTokenizationNetworkDataMapper.toTokenResult(mockServerErrorResponse)

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
            val mappedResult = cardTokenizationNetworkDataMapper.toTokenResult(mockNetworkErrorResponse)

            // Then
            mappedResult `should be instance of` TokenResult.Failure::class
            (mappedResult as TokenResult.Failure).error.cause `should be equal to` cause
        }
    }
}

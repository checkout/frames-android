package com.checkout.tokenization.mapper.request

import com.checkout.mock.TokenizationRequestTestData
import com.checkout.tokenization.request.TokenRequest
import com.checkout.tokenization.utils.TokenizationConstants
import org.amshove.kluent.internal.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CardToTokenRequestMapperTest {
    private lateinit var cardTokenRequestMapper: CardToTokenRequestMapper

    @BeforeEach
    fun setUp() {
        cardTokenRequestMapper = CardToTokenRequestMapper()
    }

    @Test
    fun `mapping of Card to TokenRequest data`() {
        // Given
        val expectedTokenRequestData = TokenRequest(
            TokenizationConstants.CARD,
            "4242424242424242",
            "10",
            "25",
            "Bob martin",
            "123",
            AddressToAddressEntityDataMapper().map(TokenizationRequestTestData.address),
            PhoneToPhoneEntityDataMapper().map(TokenizationRequestTestData.phone)
        )

        // When
        val actualTokenRequestData = cardTokenRequestMapper.map(TokenizationRequestTestData.card)

        // Then
        assertEquals(expectedTokenRequestData, actualTokenRequestData)
    }
}

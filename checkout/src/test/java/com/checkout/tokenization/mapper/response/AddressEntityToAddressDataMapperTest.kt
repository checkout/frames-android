package com.checkout.tokenization.mapper.response

import com.checkout.base.model.Country
import com.checkout.mock.TokenizationRequestTestData
import com.checkout.tokenization.model.Address
import org.amshove.kluent.internal.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class AddressEntityToAddressDataMapperTest {

    private lateinit var addressEntityToAddressDataMapper: AddressEntityToAddressDataMapper

    @BeforeEach
    fun setUp() {
        addressEntityToAddressDataMapper = AddressEntityToAddressDataMapper()
    }

    @Test
    fun `mapping of AddressEntity to Address data`() {
        // Given
        val expectedAddressData = Address(
            "Checkout.com",
            "90 Tottenham Court Road",
            "London",
            "London",
            "W1T 4TJ",
            Country.from("GB"),
        )

        // When
        val actualAddressData = addressEntityToAddressDataMapper.map(TokenizationRequestTestData.addressEntity)

        // Then
        assertEquals(expectedAddressData, actualAddressData)
    }
}

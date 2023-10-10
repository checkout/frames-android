package com.checkout.tokenization.mapper.request

import com.checkout.mock.TokenizationRequestTestData
import com.checkout.tokenization.entity.AddressEntity
import org.amshove.kluent.internal.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class AddressToAddressEntityDataMapperTest {
    private lateinit var addressToAddressEntityDataMapper: AddressToAddressEntityDataMapper

    @BeforeEach
    fun setUp() {
        addressToAddressEntityDataMapper = AddressToAddressEntityDataMapper()
    }

    @Test
    fun `mapping of Address to AddressEntity data`() {
        // Given
        val expectedAddressEntityData = AddressEntity(
            "Checkout.com",
            "90 Tottenham Court Road",
            "London",
            "London",
            "W1T 4TJ",
            "GB",
        )

        // When
        val actualAddressEntityData = addressToAddressEntityDataMapper.map(TokenizationRequestTestData.address)

        // Then
        assertEquals(expectedAddressEntityData, actualAddressEntityData)
    }
}

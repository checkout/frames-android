package com.checkout.tokenization.mapper.response

import com.checkout.base.model.Country
import com.checkout.mock.CardTokenTestData
import com.checkout.tokenization.model.Phone
import org.amshove.kluent.internal.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class PhoneEntityToPhoneDataMapperTest {
    private lateinit var phoneEntityToPhoneDataMapper: PhoneEntityToPhoneDataMapper

    @BeforeEach
    fun setUp() {
        phoneEntityToPhoneDataMapper = PhoneEntityToPhoneDataMapper("GB")
    }

    @Test
    fun `mapping of PhoneEntity to Phone data`() {
        // Given
        val expectedPhoneData = Phone(
            number = "4155552671",
            country = Country.getCountry(
                dialingCode = "44",
                iso3166Alpha2 = "GB"
            )
        )

        // When
        val actualPhoneData = phoneEntityToPhoneDataMapper.map(CardTokenTestData.phoneEntity)

        // Then
        assertEquals(expectedPhoneData, actualPhoneData)
    }
}

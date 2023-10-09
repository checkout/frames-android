package com.checkout.tokenization.mapper.response

import com.checkout.base.model.Country
import com.checkout.mock.TokenizationRequestTestData
import com.checkout.tokenization.model.Phone
import org.amshove.kluent.internal.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class PhoneEntityToPhoneDataMapperTest {
    private lateinit var phoneEntityToPhoneDataMapper: PhoneEntityToPhoneDataMapper

    @BeforeEach
    fun setUp() {
        phoneEntityToPhoneDataMapper = PhoneEntityToPhoneDataMapper()
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
        val actualPhoneData = phoneEntityToPhoneDataMapper.map(from = TokenizationRequestTestData.phoneEntity to "GB")

        // Then
        assertEquals(expectedPhoneData.number, actualPhoneData.number)
        assertEquals(expectedPhoneData.country?.dialingCode, actualPhoneData.country?.dialingCode)
        assertEquals(expectedPhoneData.country?.iso3166Alpha2, actualPhoneData.country?.iso3166Alpha2)
    }
}

package com.checkout.tokenization.mapper.request

import com.checkout.mock.TokenizationRequestTestData
import com.checkout.tokenization.entity.PhoneEntity
import org.amshove.kluent.internal.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class PhoneToPhoneEntityDataMapperTest {
    private lateinit var phoneToPhoneEntityDataMapper: PhoneToPhoneEntityDataMapper

    @BeforeEach
    fun setUp() {
        phoneToPhoneEntityDataMapper = PhoneToPhoneEntityDataMapper()
    }

    @Test
    fun `mapping of Phone to PhoneEntity data`() {
        // Given
        val expectedPhoneEntityData = PhoneEntity("4155552671", "44")

        // When
        val actualPhoneEntityData = phoneToPhoneEntityDataMapper.map(TokenizationRequestTestData.phone)

        // Then
        assertEquals(expectedPhoneEntityData, actualPhoneEntityData)
    }
}

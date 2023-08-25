package com.checkout.base.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class CountryTest {

    @Test
    fun `given valid country Iso3166Alpha2 returns correct result`() {
        // Given
        val expectedCountry = Country.UNITED_KINGDOM

        // When
        val actualCountry = Country.from("GB")

        // Then
        assertEquals(expectedCountry, actualCountry)
    }

    @Test
    fun `given blank country Iso3166Alpha2 code returns null`() {
        // When
        val actualCountry = Country.from("")

        // Then
        assertNull(actualCountry)
    }

    @Test
    fun `given full country details should returns valid country`() {
        // Given
        val expectedCountry = Country.UNITED_KINGDOM

        // When
        val actualCountry = Country.getCountry("44", "GB")

        // Then
        assertEquals(expectedCountry, actualCountry)
    }

    @Test
    fun `given country details should returns null`() {
        // When
        val actualCountry = Country.getCountry("44", null)

        // Then
        assertNull(actualCountry)
    }

    @Test
    fun `given invalid country Iso3166Alpha2 code returns null`() {
        // When
        val actualCountry = Country.from("TYTYTYTYTTYTTYYYY")

        // Then
        assertNull(actualCountry)
    }
}

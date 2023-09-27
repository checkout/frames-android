package com.checkout.base.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class CardSchemeTest {
    @Test
    fun `given visa scheme string value returns correct result`() {
        // Given
        val expectedCardScheme = CardScheme.VISA

        // When
        val actualCardScheme = CardScheme.fromString("Visa")

        // Then
        Assertions.assertEquals(expectedCardScheme, actualCardScheme)
    }

    @Test
    fun `given Diners scheme string value returns correct result`() {
        // Given
        val expectedCardScheme = CardScheme.DINERS_CLUB

        // When
        val actualCardScheme = CardScheme.fromString("Diners Club International")

        // Then
        Assertions.assertEquals(expectedCardScheme, actualCardScheme)
    }

    @Test
    fun `given scheme string containing uppercase along with underscore value returns correct result`() {
        // Given
        val expectedCardScheme = CardScheme.DINERS_CLUB

        // When
        val actualCardScheme = CardScheme.fromString("DINERS_CLUB_INTERNATIONAL")

        // Then
        Assertions.assertEquals(expectedCardScheme, actualCardScheme)
    }

    @Test
    fun `given American express scheme string value returns correct result`() {
        // Given
        val expectedCardScheme = CardScheme.AMERICAN_EXPRESS

        // When
        val actualCardScheme = CardScheme.fromString("AMERICAN EXPRESS")

        // Then
        Assertions.assertEquals(expectedCardScheme, actualCardScheme)
    }

    @Test
    fun `given invalid scheme string value returns unknown Card Scheme`() {
        // Given
        val expectedCardScheme = CardScheme.UNKNOWN

        // When
        val actualCardScheme = CardScheme.fromString("TEST CARD SCHEME")

        // Then
        Assertions.assertEquals(expectedCardScheme, actualCardScheme)
    }
}

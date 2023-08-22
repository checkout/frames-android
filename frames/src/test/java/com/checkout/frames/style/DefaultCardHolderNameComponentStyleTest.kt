package com.checkout.frames.style

import com.checkout.frames.R
import com.checkout.frames.style.component.CardHolderNameComponentStyle
import com.checkout.frames.style.component.default.DefaultCardHolderNameComponentStyle
import org.amshove.kluent.internal.assertEquals
import org.amshove.kluent.internal.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class DefaultCardHolderNameComponentStyleTest {

    lateinit var style: CardHolderNameComponentStyle

    @Test
    fun `when DefaultCardHolderNameComponentStyle is requested with isOptional true then correct style data should load`() {
        // Given
        style = DefaultCardHolderNameComponentStyle.light(isOptional = true)

        // Then
        assertTrue(style.inputStyle.isInputFieldOptional)
        assertEquals(R.string.cko_input_field_optional_info, style.inputStyle.infoStyle?.textId)
    }

    @Test
    fun `when DefaultCardHolderNameComponentStyle is requested with isOptional false then correct style data should load`() {
        // Given
        style = DefaultCardHolderNameComponentStyle.light(isOptional = false)

        // Then
        assertFalse(style.inputStyle.isInputFieldOptional)
        assertEquals(null, style.inputStyle.infoStyle?.textId)
    }
}

package com.checkout.frames.style.component

import androidx.annotation.ColorLong
import com.checkout.frames.model.TextAlign
import com.checkout.frames.model.font.Font
import com.checkout.frames.model.font.FontStyle
import com.checkout.frames.model.font.FontWeight

public data class TextStyle(
    /** Text size in sp. */
    val size: Int,
    /** The 32-bit ARGB text color. */
    @ColorLong
    val color: Long,
    /** Defines how to align text horizontally. */
    val textAlign: TextAlign = TextAlign.Start,
    /** Defines font family. */
    val font: Font = Font.Default,
    /** Defines font style, normal or italic. */
    val fontStyle: FontStyle = FontStyle.Normal,
    /** Defines font weight for the text field. */
    val fontWeight: FontWeight = FontWeight.Normal,
    /**
     * Maximum number of lines for the text to span, wrapping if necessary.
     * If the text exceeds the given number of lines, it will be truncated.
     */
    val maxLines: Int = Int.MAX_VALUE,
    /** Optional maximum symbols limit. Applicable for editable text fields. */
    val maxLength: Int? = null
)

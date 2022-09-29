package com.checkout.frames.utils.constants

import androidx.annotation.ColorLong
import com.checkout.frames.model.font.Font

public object LightStyleConstants {
    @ColorLong
    public const val fontColor: Long = 0xFF141414

    /** Text size in sp. */
    public const val fontSize: Int = 15

    public val font: Font = Font.SansSerif

    /** Max lines. **/
    public const val maxLines: Int = 1

    /** Margin in dp. */
    public const val marginTop: Int = 8

    /** Margin in dp. */
    public const val marginBottom: Int = 8

    @ColorLong
    public const val unfocusedBorderColor: Long = 0xFF8A8A8A

    @ColorLong
    public const val focusedBorderColor: Long = 0xFF0B5FF0

    @ColorLong
    public const val errorBorderColor: Long = 0xFFAD283E

    /** Leading icon height in dp. */
    public const val leadingIconHeight: Int = 16

    /** Leading icon width in dp. */
    public const val leadingIconWidth: Int = 26

    /** Leading icon start padding in dp. */
    public const val leadingIconStartPadding: Int = 20

    /** SupportedCardSchemes icon end padding in dp. */
    public const val supportedCardSchemeIconEndPadding: Int = 4

    /** Leading icon end padding in dp. */
    public const val leadingIconEndPadding: Int = 10
}

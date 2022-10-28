package com.checkout.frames.utils.constants

import androidx.annotation.ColorLong
import com.checkout.frames.model.font.Font

public object HeaderTitleConstants {
    @ColorLong
    public const val textColor: Long = 0xFF141414
    @ColorLong
    public const val backgroundColor: Long = 0xFFFFFFFF

    /** Text size in sp. */
    public const val fontSize: Int = 20

    public val font: Font = Font.SansSerif

    /** Max lines. **/
    public const val maxLines: Int = 1

    /** Leading icon size in dp. */
    public const val leadingIconSize: Int = 14
    /** Leading icon margin in dp. */
    public const val leadingIconPaddingEnd: Int = 8
    public const val leadingIconPaddingStart: Int = 8

    /** Height of the header. **/
    public const val height: Int = 56
}

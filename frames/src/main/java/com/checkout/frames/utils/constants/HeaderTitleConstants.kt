package com.checkout.frames.utils.constants

import androidx.annotation.ColorLong
import com.checkout.frames.model.font.Font

public object HeaderTitleConstants {
    @ColorLong
    public const val color: Long = 0xFF141414

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
}

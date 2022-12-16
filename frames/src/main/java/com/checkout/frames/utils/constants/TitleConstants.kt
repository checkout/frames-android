package com.checkout.frames.utils.constants

import androidx.annotation.ColorLong
import com.checkout.frames.model.font.Font

public object TitleConstants {
    @ColorLong
    public const val color: Long = 0xFF141414

    /** Text size in sp. */
    public const val fontSize: Int = 15

    public val font: Font = Font.SansSerif

    /** Max lines. **/
    public const val maxLines: Int = 1
}

package com.checkout.frames.style.component.base

import androidx.annotation.ColorLong

public data class CursorStyle(
    /** The 32-bit ARGB color for input field cursor. */
    @ColorLong
    public val cursorColor: Long,
    /** The 32-bit ARGB color for input field cursor. */
    public val errorCursorColor: Long,
    /** The 32-bit ARGB color for input field cursor handle. */
    @ColorLong
    public val cursorHandleColor: Long,
    /** The 32-bit ARGB color for input field highlighted text. */
    @ColorLong
    public val cursorHighlightColor: Long,
) {
    public constructor(cursorColor: Long) : this(cursorColor, cursorColor, cursorColor, cursorColor)
}

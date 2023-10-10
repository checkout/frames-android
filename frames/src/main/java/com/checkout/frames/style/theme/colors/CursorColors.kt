package com.checkout.frames.style.theme.colors

import androidx.annotation.ColorLong

public data class CursorColors(
    @ColorLong
    public val cursorColor: Long,
    @ColorLong
    public val errorCursorColor: Long,
    @ColorLong
    public val cursorHandleColor: Long,
    @ColorLong
    public val cursorHighlightColor: Long,
) {
    public constructor(cursorColor: Long) : this(cursorColor, cursorColor, cursorColor, cursorColor)
}

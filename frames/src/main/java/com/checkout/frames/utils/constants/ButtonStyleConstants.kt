package com.checkout.frames.utils.constants

import androidx.annotation.ColorLong
import com.checkout.frames.model.Padding

public object ButtonStyleConstants {
    /** Default button content padding in dp. */
    public val outlineContentPadding: Padding = Padding(start = 16, end = 16, top = 8, bottom = 8)
    public val solidContentPadding: Padding = Padding(start = 24, end = 24, top = 14, bottom = 16)

    /** Default outline button stroke color. **/
    @ColorLong
    public val outlineStrokeColor: Long = 0xFF8099FD

    /** Default trailing icon height in dp. **/
    public const val trailingIconHeight: Int = 12

    /** Default trailing icon padding in dp. **/
    public val trailingIconPadding: Padding = Padding(start = 12)

    @ColorLong
    public val colorTransparent: Long = 0x00000000
}

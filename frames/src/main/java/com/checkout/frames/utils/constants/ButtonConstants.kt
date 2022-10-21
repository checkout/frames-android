package com.checkout.frames.utils.constants

import com.checkout.frames.model.Shape
import com.checkout.frames.model.font.Font

public object ButtonConstants {
    public const val containerColor: Long = 0xFF0B5FF0
    public const val disabledContainerColor: Long = 0xFFD9D9D9
    public const val contentColor: Long = 0xFFFFFFFF
    public const val disabledContentColor: Long = 0xFF727272
    public const val defaultCornerRadius: Int = 8
    public val shape: Shape = Shape.None
    /** Text size in sp. */
    public const val fontSize: Int = 14
    public val font: Font = Font.SansSerif
    /** Leading icon start padding in dp. */
    public const val buttonContainerTopPadding: Int = 14
    public const val buttonContainerEndPadding: Int = 16
    public const val buttonContentEndPadding: Int = 18
    public const val buttonContentBottomPadding: Int = 6
    public const val buttonContentTopPadding: Int = 6
    public const val buttonContentStartPadding: Int = 18
}

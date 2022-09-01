package com.checkout.frames.utils.constants

import androidx.annotation.ColorLong
import androidx.annotation.DrawableRes
import com.checkout.frames.R
import com.checkout.frames.model.font.Font

public object ErrorConstants {
    @ColorLong
    public const val color: Long = 0xFFAD283E

    /** Text size in sp. */
    public const val fontSize: Int = 13

    public val font: Font = Font.SansSerif

    /** Leading error icon id. **/
    @DrawableRes
    public val leadingIconId: Int = R.drawable.cko_ic_error

    /** Leading error icon size in dp. */
    public const val leadingIconSize: Int = 15

    /** Leading error icon end padding in dp. */
    public const val leadingIconEndPadding: Int = 8

    /** Error message top padding in dp. */
    public const val errorMessageTopPadding: Int = 8
}

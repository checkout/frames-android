package com.checkout.frames.style.theme.colors

import androidx.annotation.ColorLong

public data class TextColors @JvmOverloads constructor(
    @ColorLong
    val titleColor: Long = 0xFF000000,
    @ColorLong
    val subTitleColor: Long = 0xFF000000,
    @ColorLong
    val infoColor: Long = 0xFF000000,
    @ColorLong
    val errorColor: Long = 0XFFFF0000,
)

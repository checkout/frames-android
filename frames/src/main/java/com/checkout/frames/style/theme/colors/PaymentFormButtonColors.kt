package com.checkout.frames.style.theme.colors

import androidx.annotation.ColorLong

public data class PaymentFormButtonColors @JvmOverloads constructor(
    @ColorLong
    val containerColor: Long = 0xFF0B5FF0,
    @ColorLong
    val disabledContainerColor: Long = 0xFFCCCCCC,
    @ColorLong
    val contentColor: Long = 0xFFFFFFFF,
    @ColorLong
    val disabledContentColor: Long = 0xFF000000,
)

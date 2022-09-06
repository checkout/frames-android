package com.checkout.frames.component.provider

import androidx.compose.runtime.Composable
import com.checkout.frames.style.component.CardNumberComponentStyle
import com.checkout.frames.style.component.CvvComponentStyle

internal interface ComponentProvider {
    @Composable
    fun CardNumber(style: CardNumberComponentStyle)

    @Composable
    fun Cvv(style: CvvComponentStyle)
}

package com.checkout.frames.component.provider

import androidx.compose.runtime.Composable
import com.checkout.frames.style.component.CardNumberComponentStyle

internal interface ComponentProvider {
    @Composable
    fun CardNumber(style: CardNumberComponentStyle)
}

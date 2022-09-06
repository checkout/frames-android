package com.checkout.frames.component.provider

import androidx.compose.runtime.Composable
import com.checkout.frames.component.cardnumber.CardNumberComponent
import com.checkout.frames.component.cvv.CvvComponent
import com.checkout.frames.component.expirydate.ExpiryDateComponent
import com.checkout.frames.di.base.Injector
import com.checkout.frames.style.component.CardNumberComponentStyle
import com.checkout.frames.style.component.CvvComponentStyle
import com.checkout.frames.style.component.ExpiryDateComponentStyle

internal class ComposableComponentProvider(
    private val injector: Injector
) : ComponentProvider {

    @Composable
    override fun CardNumber(style: CardNumberComponentStyle) {
        CardNumberComponent(style, injector)
    }

    @Composable
    override fun ExpiryDate(style: ExpiryDateComponentStyle) {
        ExpiryDateComponent(style, injector)
    }

    @Composable
    override fun Cvv(style: CvvComponentStyle) {
        CvvComponent(style, injector)
    }
}

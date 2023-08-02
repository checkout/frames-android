package com.checkout.frames.component.provider

import androidx.compose.runtime.Composable
import com.checkout.frames.component.addresssummary.AddressSummaryComponent
import com.checkout.frames.component.cardholdername.CardHolderNameComponent
import com.checkout.frames.component.cardnumber.CardNumberComponent
import com.checkout.frames.component.cardscheme.CardSchemeComponent
import com.checkout.frames.component.cvv.CvvComponent
import com.checkout.frames.component.expirydate.ExpiryDateComponent
import com.checkout.frames.component.paybutton.PayButtonComponent
import com.checkout.frames.di.base.Injector
import com.checkout.frames.style.component.CardHolderNameComponentStyle
import com.checkout.frames.style.component.CardNumberComponentStyle
import com.checkout.frames.style.component.CardSchemeComponentStyle
import com.checkout.frames.style.component.CvvComponentStyle
import com.checkout.frames.style.component.ExpiryDateComponentStyle
import com.checkout.frames.style.component.PayButtonComponentStyle
import com.checkout.frames.style.component.addresssummary.AddressSummaryComponentStyle

internal class ComposableComponentProvider(
    private val injector: Injector
) : ComponentProvider {

    @Composable
    override fun CardScheme(style: CardSchemeComponentStyle) {
      CardSchemeComponent(style, injector)
    }

    @Composable
    override fun CardHolderName(style: CardHolderNameComponentStyle) {
        CardHolderNameComponent(style, injector)
    }

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

    @Composable
    override fun AddressSummary(style: AddressSummaryComponentStyle, goToBillingAddressForm: () -> Unit) {
        AddressSummaryComponent(style, injector, goToBillingAddressForm)
    }

    @Composable
    override fun PayButton(style: PayButtonComponentStyle) {
        PayButtonComponent(style = style, injector = injector)
    }
}

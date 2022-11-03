package com.checkout.frames.di.module

import com.checkout.base.model.CardScheme
import com.checkout.frames.di.component.CardSchemeViewModelSubComponent
import com.checkout.frames.di.component.AddressSummaryViewModelSubComponent
import com.checkout.frames.di.component.BillingFormViewModelSubComponent
import com.checkout.frames.di.component.CardNumberViewModelSubComponent
import com.checkout.frames.di.component.CountryPickerViewModelSubComponent
import com.checkout.frames.di.component.CountryViewModelSubComponent
import com.checkout.frames.di.component.CvvViewModelSubComponent
import com.checkout.frames.di.component.ExpiryDateViewModelSubComponent
import com.checkout.frames.di.screen.PaymentDetailsViewModelSubComponent
import com.checkout.frames.screen.manager.PaymentFormStateManager
import com.checkout.frames.screen.manager.PaymentStateManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    subcomponents = [
        CardNumberViewModelSubComponent::class,
        CvvViewModelSubComponent::class,
        ExpiryDateViewModelSubComponent::class,
        PaymentDetailsViewModelSubComponent::class,
        CountryViewModelSubComponent::class,
        CountryPickerViewModelSubComponent::class,
        BillingFormViewModelSubComponent::class,
        CardSchemeViewModelSubComponent::class,
        AddressSummaryViewModelSubComponent::class
    ]
)
internal class PaymentModule {
    companion object {
        @Provides
        @Singleton
        fun paymentStateManager(
            supportedCardSchemes: List<CardScheme>
        ): PaymentStateManager =
            PaymentFormStateManager(supportedCardSchemes)
    }
}

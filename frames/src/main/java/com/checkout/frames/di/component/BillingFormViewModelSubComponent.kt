package com.checkout.frames.di.component

import com.checkout.frames.di.module.BillingAddressDetailsModule
import com.checkout.frames.screen.billingaddress.billingaddressdetails.BillingAddressDetailsViewModel
import com.checkout.frames.style.screen.BillingAddressDetailsStyle
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [BillingAddressDetailsModule::class])
internal interface BillingFormViewModelSubComponent {
    val billingAddressDetailsViewModel: BillingAddressDetailsViewModel

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun style(style: BillingAddressDetailsStyle): Builder

        fun build(): BillingFormViewModelSubComponent
    }
}

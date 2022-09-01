package com.checkout.frames.di.screen

import com.checkout.frames.component.provider.ComponentProvider
import com.checkout.frames.screen.paymentdetails.PaymentDetailsViewModel
import com.checkout.frames.style.screen.PaymentDetailsStyle
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent
internal interface PaymentDetailsViewModelSubComponent {
    val paymentDetailsViewModel: PaymentDetailsViewModel

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun style(style: PaymentDetailsStyle): Builder

        @BindsInstance
        fun componentProvider(componentProvider: ComponentProvider): Builder

        fun build(): PaymentDetailsViewModelSubComponent
    }
}

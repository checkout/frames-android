package com.checkout.frames.di.module

import com.checkout.frames.di.component.CardNumberViewModelSubComponent
import com.checkout.frames.di.component.CvvViewModelSubComponent
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
        PaymentDetailsViewModelSubComponent::class
    ]
)
internal class PaymentModule {
    companion object {
        @Provides
        @Singleton
        fun paymentStateManager(): PaymentStateManager = PaymentFormStateManager()
    }
}

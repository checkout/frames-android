package com.checkout.frames.di.component

import android.content.Context
import com.checkout.base.model.Environment
import com.checkout.frames.component.cardnumber.CardNumberViewModel
import com.checkout.frames.component.expirydate.ExpiryDateViewModel
import com.checkout.frames.di.module.ValidationModule
import com.checkout.frames.di.module.StylesModule
import com.checkout.frames.screen.paymentdetails.PaymentDetailsViewModel
import com.checkout.frames.screen.paymentform.PaymentFormViewModel
import com.checkout.frames.utils.constants.PUBLIC_KEY
import dagger.BindsInstance
import dagger.Component
import javax.inject.Named

@Component(modules = [ValidationModule::class, StylesModule::class])
internal abstract class FramesDIComponent {

    /** Screen **/
    abstract fun inject(factory: PaymentFormViewModel.Factory)
    abstract fun inject(factory: PaymentDetailsViewModel.Factory)

    /** Component **/
    abstract fun inject(factory: CardNumberViewModel.Factory)

    abstract fun inject(factory: ExpiryDateViewModel.Factory)

    @Component.Builder
    interface Builder {
        @Named(PUBLIC_KEY)
        @BindsInstance
        fun publicKey(publicKey: String): Builder

        @BindsInstance
        fun context(context: Context): Builder

        @BindsInstance
        fun environment(environment: Environment): Builder

        fun build(): FramesDIComponent
    }
}

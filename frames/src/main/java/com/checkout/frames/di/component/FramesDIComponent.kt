package com.checkout.frames.di.component

import com.checkout.base.model.CardScheme
import com.checkout.base.usecase.UseCase
import com.checkout.frames.component.addresssummary.AddressSummaryViewModel
import com.checkout.frames.component.cardholdername.CardHolderNameViewModel
import com.checkout.frames.component.cardnumber.CardNumberViewModel
import com.checkout.frames.component.cardscheme.CardSchemeViewModel
import com.checkout.frames.component.country.CountryViewModel
import com.checkout.frames.component.cvv.CvvViewModel
import com.checkout.frames.component.expirydate.ExpiryDateViewModel
import com.checkout.frames.component.paybutton.PayButtonViewModel
import com.checkout.frames.di.CLOSE_PAYMENT_FLOW_DI
import com.checkout.frames.di.module.PaymentModule
import com.checkout.frames.di.module.StylesModule
import com.checkout.frames.di.module.ValidationModule
import com.checkout.frames.model.request.InternalCardTokenRequest
import com.checkout.frames.screen.billingaddress.billingaddressdetails.BillingAddressDetailsViewModel
import com.checkout.frames.screen.countrypicker.CountryPickerViewModel
import com.checkout.frames.screen.paymentdetails.PaymentDetailsViewModel
import com.checkout.frames.screen.paymentform.PaymentFormViewModel
import com.checkout.frames.screen.paymentform.model.PrefillData
import com.checkout.logging.Logger
import com.checkout.logging.model.LoggingEvent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [ValidationModule::class, StylesModule::class, PaymentModule::class])
@Suppress("TooManyFunctions")
internal abstract class FramesDIComponent {

    /** Screen **/
    abstract fun inject(factory: PaymentFormViewModel.Factory)
    abstract fun inject(factory: PaymentDetailsViewModel.Factory)
    abstract fun inject(factory: CountryPickerViewModel.Factory)
    abstract fun inject(factory: BillingAddressDetailsViewModel.Factory)

    /** Component **/
    abstract fun inject(factory: CardNumberViewModel.Factory)
    abstract fun inject(factory: CardHolderNameViewModel.Factory)
    abstract fun inject(factory: CvvViewModel.Factory)
    abstract fun inject(factory: ExpiryDateViewModel.Factory)
    abstract fun inject(factory: CardSchemeViewModel.Factory)
    abstract fun inject(factory: CountryViewModel.Factory)
    abstract fun inject(factory: AddressSummaryViewModel.Factory)
    abstract fun inject(factory: PayButtonViewModel.Factory)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun cardTokenizationUseCase(cardTokenizationUseCase: UseCase<InternalCardTokenRequest, Unit>): Builder

        @BindsInstance
        fun closePaymentFlowUseCase(
            @Named(CLOSE_PAYMENT_FLOW_DI)
            closePaymentFlowUseCase: UseCase<Unit, Unit>,
        ): Builder

        @BindsInstance
        fun logger(logger: Logger<LoggingEvent>): Builder

        @BindsInstance
        fun supportedCardSchemes(supportedCardSchemeList: List<CardScheme>): Builder

        @BindsInstance
        fun prefillData(cardHolderName: PrefillData?): Builder

        fun build(): FramesDIComponent
    }
}

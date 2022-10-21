package com.checkout.frames.di.component

import android.content.Context
import com.checkout.base.model.CardScheme
import com.checkout.base.model.Environment
import com.checkout.frames.component.addresssummary.AddressSummaryViewModel
import com.checkout.frames.component.cardnumber.CardNumberViewModel
import com.checkout.frames.component.cardscheme.CardSchemeViewModel
import com.checkout.frames.component.country.CountryViewModel
import com.checkout.frames.component.expirydate.ExpiryDateViewModel
import com.checkout.frames.component.cvv.CvvViewModel
import com.checkout.frames.di.module.PaymentModule
import com.checkout.frames.di.module.ValidationModule
import com.checkout.frames.di.module.StylesModule
import com.checkout.frames.screen.billingaddress.billingaddressdetails.BillingAddressDetailsViewModel
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.AddressField
import com.checkout.frames.screen.billingaddress.billingaddressform.BillingAddressFormViewModel
import com.checkout.frames.screen.countrypicker.CountryPickerViewModel
import com.checkout.frames.screen.paymentdetails.PaymentDetailsViewModel
import com.checkout.frames.screen.paymentform.PaymentFormViewModel
import com.checkout.frames.utils.constants.PUBLIC_KEY
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
    abstract fun inject(factory: BillingAddressFormViewModel.Factory)

    /** Component **/
    abstract fun inject(factory: CardNumberViewModel.Factory)
    abstract fun inject(factory: CvvViewModel.Factory)
    abstract fun inject(factory: ExpiryDateViewModel.Factory)
    abstract fun inject(factory: CardSchemeViewModel.Factory)
    abstract fun inject(factory: CountryViewModel.Factory)
    abstract fun inject(factory: AddressSummaryViewModel.Factory)

    @Component.Builder
    interface Builder {
        @Named(PUBLIC_KEY)
        @BindsInstance
        fun publicKey(publicKey: String): Builder

        @BindsInstance
        fun context(context: Context): Builder

        @BindsInstance
        fun environment(environment: Environment): Builder

        @BindsInstance
        fun supportedCardSchemes(supportedCardSchemeList: List<CardScheme>): Builder

        @BindsInstance
        fun billingFormFields(billingFormFieldList: List<AddressField>): Builder

        fun build(): FramesDIComponent
    }
}

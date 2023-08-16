package com.checkout.frames.di.injector

import android.content.Context
import com.checkout.CheckoutApiServiceFactory
import com.checkout.base.model.CardScheme
import com.checkout.base.model.Environment
import com.checkout.frames.BuildConfig
import com.checkout.frames.component.addresssummary.AddressSummaryViewModel
import com.checkout.frames.component.cardnumber.CardNumberViewModel
import com.checkout.frames.component.country.CountryViewModel
import com.checkout.frames.component.cardscheme.CardSchemeViewModel
import com.checkout.frames.component.cvv.CvvViewModel
import com.checkout.frames.component.expirydate.ExpiryDateViewModel
import com.checkout.frames.component.paybutton.PayButtonViewModel
import com.checkout.frames.di.base.InjectionClient
import com.checkout.frames.di.base.Injector
import com.checkout.frames.di.component.DaggerFramesDIComponent
import com.checkout.frames.di.component.FramesDIComponent
import com.checkout.frames.logging.PaymentFormEventType
import com.checkout.frames.screen.billingaddress.billingaddressdetails.BillingAddressDetailsViewModel
import com.checkout.frames.screen.countrypicker.CountryPickerViewModel
import com.checkout.frames.screen.paymentdetails.PaymentDetailsViewModel
import com.checkout.frames.screen.paymentform.PaymentFormViewModel
import com.checkout.frames.api.PaymentFlowHandler
import com.checkout.frames.component.cardholdername.CardHolderNameViewModel
import com.checkout.frames.usecase.CardTokenizationUseCase
import com.checkout.frames.usecase.ClosePaymentFlowUseCase
import com.checkout.frames.utils.extensions.logEvent
import com.checkout.logging.EventLoggerProvider

internal class FramesInjector(private val component: FramesDIComponent) : Injector {

    override fun inject(client: InjectionClient) {
        when (client) {
            is CardNumberViewModel.Factory -> component.inject(client)
            is CardHolderNameViewModel.Factory -> component.inject(client)
            is ExpiryDateViewModel.Factory -> component.inject(client)
            is CvvViewModel.Factory -> component.inject(client)
            is PaymentDetailsViewModel.Factory -> component.inject(client)
            is PaymentFormViewModel.Factory -> component.inject(client)
            is CountryViewModel.Factory -> component.inject(client)
            is CountryPickerViewModel.Factory -> component.inject(client)
            is CardSchemeViewModel.Factory -> component.inject(client)
            is AddressSummaryViewModel.Factory -> component.inject(client)
            is PayButtonViewModel.Factory -> component.inject(client)
            is BillingAddressDetailsViewModel.Factory -> component.inject(client)
            else -> throw IllegalArgumentException("Invalid injection request for ${client.javaClass.name}.")
        }
    }

    companion object {
        internal fun create(
            publicKey: String,
            context: Context,
            environment: Environment,
            paymentFlowHandler: PaymentFlowHandler,
            supportedCardSchemeList: List<CardScheme> = emptyList(),
            cardHolderName: String = ""
        ): Injector {
            val logger = EventLoggerProvider.provide().apply {
                setup(context, environment, BuildConfig.LOGGING_IDENTIFIER, BuildConfig.PRODUCT_VERSION)
                logEvent(PaymentFormEventType.INITIALISED)
            }
            val closePaymentFlowUseCase = ClosePaymentFlowUseCase(paymentFlowHandler::onBackPressed)
            val cardTokenizationUseCase = CardTokenizationUseCase(
                CheckoutApiServiceFactory.create(publicKey, environment, context),
                paymentFlowHandler::onSubmit,
                paymentFlowHandler::onSuccess,
                paymentFlowHandler::onFailure
            )
            return FramesInjector(
                DaggerFramesDIComponent.builder()
                    .logger(logger)
                    .cardTokenizationUseCase(cardTokenizationUseCase)
                    .closePaymentFlowUseCase(closePaymentFlowUseCase)
                    .supportedCardSchemes(supportedCardSchemeList)
                    .cardHolderName(cardHolderName)
                    .build()
            )
        }
    }
}

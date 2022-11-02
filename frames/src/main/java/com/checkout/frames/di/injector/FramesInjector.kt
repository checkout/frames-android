package com.checkout.frames.di.injector

import android.content.Context
import com.checkout.CheckoutApiServiceFactory
import com.checkout.base.model.CardScheme
import com.checkout.base.model.Environment
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
import com.checkout.frames.screen.billingaddress.billingaddressdetails.BillingAddressDetailsViewModel
import com.checkout.frames.screen.countrypicker.CountryPickerViewModel
import com.checkout.frames.screen.paymentdetails.PaymentDetailsViewModel
import com.checkout.frames.screen.paymentform.PaymentFormViewModel
import com.checkout.frames.paymentflow.PaymentFlowHandler
import com.checkout.frames.paymentflow.CardTokenizationUseCase
import com.checkout.frames.paymentflow.ClosePaymentFlowUseCase
import com.checkout.logging.EventLoggerProvider
import java.lang.ref.WeakReference

internal class FramesInjector(private val component: FramesDIComponent) : Injector {

    override fun inject(client: InjectionClient) {
        when (client) {
            is CardNumberViewModel.Factory -> component.inject(client)
            is ExpiryDateViewModel.Factory -> component.inject(client)
            is CvvViewModel.Factory -> component.inject(client)
            is PaymentDetailsViewModel.Factory -> component.inject(client)
            is PaymentFormViewModel.Factory -> component.inject(client)
            is CountryViewModel.Factory -> component.inject(client)
            is CountryPickerViewModel.Factory -> component.inject(client)
            is CardSchemeViewModel.Factory -> component.inject(client)
            is AddressSummaryViewModel.Factory -> component.inject(client)
            is PayButtonViewModel.Factory -> component.inject(client)
            else -> throw IllegalArgumentException("Invalid injection request for ${client.javaClass.name}.")
        }
    }

    companion object {
        private var weakInjector: WeakReference<Injector>? = null

        internal fun create(
            publicKey: String,
            context: Context,
            environment: Environment,
            paymentFlowHandler: PaymentFlowHandler,
            supportedCardSchemeList: List<CardScheme> = emptyList()
        ): Injector = weakInjector?.get() ?: run {
            val logger = EventLoggerProvider.provide().apply { setup(context, environment) }
            val closePaymentFlowUseCase = ClosePaymentFlowUseCase(paymentFlowHandler::onClose)
            val cardTokenizationUseCase = CardTokenizationUseCase(
                CheckoutApiServiceFactory.create(publicKey, environment, context),
                paymentFlowHandler::onSuccess,
                paymentFlowHandler::onFailure
            )
            val injector = FramesInjector(
                DaggerFramesDIComponent.builder()
                    .logger(logger)
                    .cardTokenizationUseCase(cardTokenizationUseCase)
                    .closePaymentFlowUseCase(closePaymentFlowUseCase)
                    .supportedCardSchemes(supportedCardSchemeList)
                    .build()
            )
            weakInjector = WeakReference(injector)
            injector
        }
    }
}

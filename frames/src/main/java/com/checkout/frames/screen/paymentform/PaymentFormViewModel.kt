package com.checkout.frames.screen.paymentform

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.checkout.base.model.CardScheme
import com.checkout.base.model.Environment
import com.checkout.frames.di.base.InjectionClient
import com.checkout.frames.di.base.Injector
import com.checkout.frames.di.injector.FramesInjector
import javax.inject.Inject

internal class PaymentFormViewModel @Inject internal constructor() : ViewModel() {

    lateinit var injector: Injector

    internal class Factory(
        private val publicKey: String,
        private val context: Context,
        private val environment: Environment,
        private val supportedCardSchemes: List<CardScheme> = emptyList()
    ) : ViewModelProvider.Factory, InjectionClient {

        @Inject
        lateinit var viewModel: PaymentFormViewModel

        private lateinit var injector: Injector

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            injector = FramesInjector.create(
                publicKey,
                context,
                environment,
                supportedCardSchemes
            )

            injector.inject(this)

            viewModel.injector = injector

            return viewModel as T
        }
    }
}

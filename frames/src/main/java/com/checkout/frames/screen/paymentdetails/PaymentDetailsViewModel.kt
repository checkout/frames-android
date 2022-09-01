package com.checkout.frames.screen.paymentdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.checkout.frames.component.provider.ComponentProvider
import com.checkout.frames.component.provider.ComposableComponentProvider
import com.checkout.frames.di.base.InjectionClient
import com.checkout.frames.di.base.Injector
import com.checkout.frames.di.screen.PaymentDetailsViewModelSubComponent
import com.checkout.frames.style.screen.PaymentDetailsStyle
import javax.inject.Inject
import javax.inject.Provider

internal class PaymentDetailsViewModel @Inject constructor(
    val componentProvider: ComponentProvider,
    val style: PaymentDetailsStyle
) : ViewModel() {

    internal class Factory(
        private val injector: Injector,
        private val style: PaymentDetailsStyle
    ) : ViewModelProvider.Factory, InjectionClient {

        @Inject
        lateinit var subComponentProvider: Provider<PaymentDetailsViewModelSubComponent.Builder>

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            injector.inject(this)
            return subComponentProvider.get()
                .style(style)
                .componentProvider(ComposableComponentProvider(injector))
                .build().paymentDetailsViewModel as T
        }
    }
}

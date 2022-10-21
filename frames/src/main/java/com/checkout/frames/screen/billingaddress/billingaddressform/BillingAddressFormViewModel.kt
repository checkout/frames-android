package com.checkout.frames.screen.billingaddress.billingaddressform

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.checkout.frames.di.base.InjectionClient
import com.checkout.frames.di.base.Injector
import javax.inject.Inject

internal class BillingAddressFormViewModel @Inject constructor() : ViewModel() {
    lateinit var injector: Injector

    val goBack = mutableStateOf(false)

    internal class Factory(private val injector: Injector) : ViewModelProvider.Factory, InjectionClient {

        @Inject
        lateinit var viewModel: BillingAddressFormViewModel

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            injector.inject(this)
            viewModel.injector = injector

            return viewModel as T
        }
    }
}

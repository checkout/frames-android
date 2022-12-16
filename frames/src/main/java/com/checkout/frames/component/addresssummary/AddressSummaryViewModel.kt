package com.checkout.frames.component.addresssummary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.checkout.base.mapper.Mapper
import com.checkout.frames.di.base.InjectionClient
import com.checkout.frames.di.base.Injector
import com.checkout.frames.di.component.AddressSummaryViewModelSubComponent
import com.checkout.frames.screen.manager.PaymentStateManager
import com.checkout.frames.style.component.addresssummary.AddressSummaryComponentStyle
import com.checkout.frames.style.view.addresssummary.AddressSummaryComponentViewStyle
import com.checkout.frames.utils.extensions.summary
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

internal class AddressSummaryViewModel @Inject constructor(
    private val style: AddressSummaryComponentStyle,
    private val paymentStateManager: PaymentStateManager,
    private val componentStateMapper: Mapper<AddressSummaryComponentStyle, AddressSummaryComponentState>,
    private val componentStyleMapper: Mapper<AddressSummaryComponentStyle, AddressSummaryComponentViewStyle>
) : ViewModel() {

    val componentState = provideState(style)
    val componentStyle = provideViewStyle(style)

    fun prepare() = viewModelScope.launch {
        paymentStateManager.billingAddress.collect { billingAddress ->
            componentState.addressPreviewState.text.value =
                if (paymentStateManager.isBillingAddressValid.value) billingAddress.summary() else ""
        }
    }

    private fun provideViewStyle(style: AddressSummaryComponentStyle): AddressSummaryComponentViewStyle {
        val viewStyle = componentStyleMapper.map(style)

        viewStyle.addAddressButtonStyle.textStyle.textMaxWidth = true
        viewStyle.summarySectionStyle.editAddressButtonStyle.textStyle.textMaxWidth = true

        return viewStyle
    }

    private fun provideState(style: AddressSummaryComponentStyle): AddressSummaryComponentState =
        componentStateMapper.map(style).apply {
            addAddressButtonState.isEnabled.value = true
            editAddressButtonState.isEnabled.value = true
        }

    internal class Factory(
        private val injector: Injector,
        private val style: AddressSummaryComponentStyle
    ) : ViewModelProvider.Factory, InjectionClient {

        @Inject
        lateinit var subComponentProvider: Provider<AddressSummaryViewModelSubComponent.Builder>

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            injector.inject(this)
            return subComponentProvider.get()
                .style(style)
                .build().addressSummaryViewModel as T
        }
    }
}

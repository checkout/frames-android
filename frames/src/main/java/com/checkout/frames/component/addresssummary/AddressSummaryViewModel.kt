package com.checkout.frames.component.addresssummary

import android.text.BidiFormatter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.checkout.base.mapper.Mapper
import com.checkout.frames.di.base.InjectionClient
import com.checkout.frames.di.base.Injector
import com.checkout.frames.di.component.AddressSummaryViewModelSubComponent
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingAddress.Companion.isEdited
import com.checkout.frames.screen.manager.PaymentStateManager
import com.checkout.frames.style.component.addresssummary.AddressSummaryComponentStyle
import com.checkout.frames.style.view.addresssummary.AddressSummaryComponentViewStyle
import com.checkout.frames.utils.extensions.summary
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

internal class AddressSummaryViewModel @Inject constructor(
    style: AddressSummaryComponentStyle,
    private val paymentStateManager: PaymentStateManager,
    private val componentStateMapper: Mapper<AddressSummaryComponentStyle, AddressSummaryComponentState>,
    private val componentStyleMapper: Mapper<AddressSummaryComponentStyle, AddressSummaryComponentViewStyle>,
) : ViewModel() {

    val componentState = provideState(style)
    val componentStyle = provideViewStyle(style)

    fun prepare(bidiFormatter: BidiFormatter = BidiFormatter.getInstance()) = viewModelScope.launch {
        paymentStateManager.billingAddress.collect { billingAddress ->
            componentState.addressPreviewState.text.value =
                if (paymentStateManager.billingAddress.value.isEdited() &&
                    paymentStateManager.isBillingAddressEnabled.value
                ) {
                    billingAddress.summary(bidiFormatter)
                } else {
                    ""
                }
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
        private val style: AddressSummaryComponentStyle,
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

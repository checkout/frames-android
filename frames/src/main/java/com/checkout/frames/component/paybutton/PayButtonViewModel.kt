package com.checkout.frames.component.paybutton

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.checkout.base.mapper.Mapper
import com.checkout.base.usecase.UseCase
import com.checkout.frames.di.base.InjectionClient
import com.checkout.frames.di.base.Injector
import com.checkout.frames.di.component.PayButtonViewModelSubComponent
import com.checkout.frames.screen.manager.PaymentStateManager
import com.checkout.frames.style.component.PayButtonComponentStyle
import com.checkout.frames.style.component.base.ButtonStyle
import com.checkout.frames.style.view.InternalButtonViewStyle
import com.checkout.frames.tokenization.InternalCardTokenRequest
import com.checkout.frames.utils.extensions.toExpiryDate
import com.checkout.frames.view.InternalButtonState
import com.checkout.tokenization.model.Card
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

internal class PayButtonViewModel @Inject constructor(
    private val style: PayButtonComponentStyle,
    private val paymentStateManager: PaymentStateManager,
    private val cardTokenizationUseCase: UseCase<InternalCardTokenRequest, Unit>,
    private val buttonStyleMapper: Mapper<ButtonStyle, InternalButtonViewStyle>,
    private val buttonStateMapper: Mapper<ButtonStyle, InternalButtonState>
) : ViewModel() {

    val buttonStyle = provideButtonViewStyle()
    val buttonState = buttonStateMapper.map(style.buttonStyle)

    fun prepare() {
        viewModelScope.launch {
            paymentStateManager.isReadyForTokenization.collect {
                buttonState.isEnabled.value = it
            }
        }
    }

    fun pay() {
        val onPaymentFinished = { buttonState.isEnabled.value = true }
        val request = InternalCardTokenRequest(
            provideCardData(),
            onSuccess = onPaymentFinished,
            onFailure = onPaymentFinished
        )

        buttonState.isEnabled.value = false
        cardTokenizationUseCase.execute(request)
    }

    private fun provideButtonViewStyle(): InternalButtonViewStyle = buttonStyleMapper.map(style.buttonStyle)
        .apply { modifier = modifier.fillMaxWidth() }

    private fun provideCardData(): Card = with(paymentStateManager) {
        val billingAddress = billingAddress.value
        Card(
            expiryDate.value.toExpiryDate(),
            billingAddress.name,
            cardNumber.value,
            cvv.value,
            billingAddress.address,
            billingAddress.phone
        )
    }

    internal class Factory(
        private val injector: Injector,
        private val style: PayButtonComponentStyle
    ) : ViewModelProvider.Factory, InjectionClient {

        @Inject
        lateinit var subComponentProvider: Provider<PayButtonViewModelSubComponent.Builder>

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            injector.inject(this)
            return subComponentProvider.get()
                .style(style)
                .build().payButtonViewModel as T
        }
    }
}

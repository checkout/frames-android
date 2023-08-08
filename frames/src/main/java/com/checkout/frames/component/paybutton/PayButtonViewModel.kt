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
import com.checkout.frames.logging.PaymentFormEventType
import com.checkout.frames.model.request.InternalCardTokenRequest
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingAddress.Companion.isEdited
import com.checkout.frames.screen.manager.PaymentStateManager
import com.checkout.frames.style.component.PayButtonComponentStyle
import com.checkout.frames.style.component.base.ButtonStyle
import com.checkout.frames.style.view.InternalButtonViewStyle
import com.checkout.frames.utils.extensions.logEvent
import com.checkout.frames.utils.extensions.toExpiryDate
import com.checkout.frames.view.InternalButtonState
import com.checkout.logging.Logger
import com.checkout.logging.model.LoggingEvent
import com.checkout.tokenization.model.Card
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

internal class PayButtonViewModel @Inject constructor(
    private val style: PayButtonComponentStyle,
    private val paymentStateManager: PaymentStateManager,
    private val cardTokenizationUseCase: UseCase<InternalCardTokenRequest, Unit>,
    private val buttonStyleMapper: Mapper<ButtonStyle, InternalButtonViewStyle>,
    buttonStateMapper: Mapper<ButtonStyle, InternalButtonState>,
    private val logger: Logger<LoggingEvent>
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
        logger.logEvent(PaymentFormEventType.SUBMITTED)
        cardTokenizationUseCase.execute(request)
    }

    private fun provideButtonViewStyle(): InternalButtonViewStyle = buttonStyleMapper.map(style.buttonStyle)
        .apply { modifier = modifier.fillMaxWidth() }

    private fun provideCardData(): Card = with(paymentStateManager) {
        // Get the BillingAddress value only if "the billing address is enabled" and "the address is edited".
        val address = billingAddress.value.takeIf { isBillingAddressEnabled.value && it.isEdited() }

        // Get the cardholder name from payment form and if it is empty take it from the Billing address
        val name = cardHolderName.value.ifEmpty { address?.name }

        Card(
            expiryDate.value.toExpiryDate(),
            name,
            cardNumber.value,
            cvv.value,
            address?.address,
            address?.phone
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

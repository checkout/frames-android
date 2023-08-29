package com.checkout.frames.screen.paymentdetails

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.checkout.base.mapper.Mapper
import com.checkout.base.usecase.UseCase
import com.checkout.frames.component.provider.ComponentProvider
import com.checkout.frames.component.provider.ComposableComponentProvider
import com.checkout.frames.di.CLOSE_PAYMENT_FLOW_DI
import com.checkout.frames.di.base.InjectionClient
import com.checkout.frames.di.base.Injector
import com.checkout.frames.di.screen.PaymentDetailsViewModelSubComponent
import com.checkout.frames.logging.PaymentFormEventType
import com.checkout.frames.mapper.ImageStyleToClickableComposableImageMapper
import com.checkout.frames.model.request.ImageStyleToClickableImageRequest
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingAddress.Companion.isEdited
import com.checkout.frames.screen.manager.PaymentStateManager
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.style.screen.PaymentDetailsStyle
import com.checkout.frames.style.view.TextLabelViewStyle
import com.checkout.frames.utils.extensions.isValid
import com.checkout.frames.utils.extensions.logEvent
import com.checkout.frames.utils.extensions.logEventWithLocale
import com.checkout.frames.view.TextLabelState
import com.checkout.logging.Logger
import com.checkout.logging.model.LoggingEvent
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider

@SuppressWarnings("LongParameterList")
internal class PaymentDetailsViewModel @Inject constructor(
    val componentProvider: ComponentProvider,
    private val textLabelStyleMapper: Mapper<TextLabelStyle, TextLabelViewStyle>,
    private val textLabelStateMapper: Mapper<TextLabelStyle?, TextLabelState>,
    containerMapper: Mapper<ContainerStyle, Modifier>,
    private val clickableImageStyleMapper: ImageStyleToClickableComposableImageMapper,
    @Named(CLOSE_PAYMENT_FLOW_DI)
    private val closePaymentFlowUseCase: UseCase<Unit, Unit>,
    private val paymentStateManager: PaymentStateManager,
    private val logger: Logger<LoggingEvent>,
    private val style: PaymentDetailsStyle
) : ViewModel() {

    val headerStyle: TextLabelViewStyle = provideHeaderViewStyle()
    val headerState: TextLabelState = provideHeaderState()
    val fieldsContainerModifier: Modifier = containerMapper.map(style.fieldsContainerStyle)

    init {
        val isCvvValidByDefault = style.cvvStyle == null
        val isCardHolderNameValidByDefault = provideIsCardHolderNameValid()
        val isBillingAddressValidEnabled = style.addressSummaryStyle != null
        val isBillingAddressValidByDefault = provideIsBillingAddressValid()
        paymentStateManager.resetPaymentState(
            isCvvValidByDefault,
            isCardHolderNameValidByDefault,
            isBillingAddressValidByDefault,
            isBillingAddressValidEnabled
        )
        logger.logEventWithLocale(PaymentFormEventType.PRESENTED)
    }

    internal fun provideIsCardHolderNameValid(): Boolean {
        val isCardHolderNamePrefilled =
            paymentStateManager.cardHolderName.value.isNotBlank() && style.cardHolderNameStyle != null
         return isCardHolderNamePrefilled || style.cardHolderNameStyle?.inputStyle?.isInputFieldOptional ?: true
    }

    internal fun provideIsBillingAddressValid(): Boolean {
        val isBillingAddressPrefilled =
            style.addressSummaryStyle != null &&
                    paymentStateManager.billingAddress.value.isEdited() &&
                    paymentStateManager.billingAddress.value.isValid()

        return if (isBillingAddressPrefilled) true else style.addressSummaryStyle?.isOptional ?: true
    }

    @VisibleForTesting
    fun onClose() {
        logger.logEvent(PaymentFormEventType.CANCELED)
        closePaymentFlowUseCase.execute(Unit)
    }

    private fun provideHeaderViewStyle(): TextLabelViewStyle = with(style.paymentDetailsHeaderStyle) {
        textLabelStyleMapper.map(
            TextLabelStyle(text, textId, textStyle, containerStyle = containerStyle)
        ).apply { modifier = modifier.fillMaxWidth() }
    }

    private fun provideHeaderState(): TextLabelState = with(style.paymentDetailsHeaderStyle) {
        val imageRequest = ImageStyleToClickableImageRequest(backIconStyle) { onClose() }

        textLabelStateMapper.map(
            TextLabelStyle(text, textId, textStyle, containerStyle = containerStyle)
        ).apply { leadingIcon.value = clickableImageStyleMapper.map(imageRequest) }
    }

    fun resetCountrySelection() {
        with(paymentStateManager) {
            if (selectedCountry.value != billingAddress.value.address?.country) {
                selectedCountry.value = billingAddress.value.address?.country
            }
        }
    }

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

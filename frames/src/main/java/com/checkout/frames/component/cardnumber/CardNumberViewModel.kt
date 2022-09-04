package com.checkout.frames.component.cardnumber

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.checkout.base.mapper.Mapper
import com.checkout.base.model.CardScheme
import com.checkout.frames.R
import com.checkout.frames.component.base.InputComponentState
import com.checkout.frames.di.base.InjectionClient
import com.checkout.frames.di.base.Injector
import com.checkout.frames.di.component.CardNumberViewModelSubComponent
import com.checkout.frames.mapper.ImageStyleToDynamicComposableImageMapper
import com.checkout.frames.model.request.ImageStyleToDynamicImageRequest
import com.checkout.frames.screen.manager.PaymentStateManager
import com.checkout.frames.style.component.CardNumberComponentStyle
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.view.InputComponentViewStyle
import com.checkout.validation.api.CardValidator
import com.checkout.validation.model.ValidationResult
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Provider

internal class CardNumberViewModel @Inject constructor(
    private val paymentStateManager: PaymentStateManager,
    private val cardValidator: CardValidator,
    private val inputComponentStyleMapper: Mapper<InputComponentStyle, InputComponentViewStyle>,
    private val inputComponentStateMapper: Mapper<InputComponentStyle, InputComponentState>,
    private val imageMapper: ImageStyleToDynamicComposableImageMapper,
    private val style: CardNumberComponentStyle
) : ViewModel() {

    val componentState = provideViewState(style)
    val componentStyle = provideViewStyle(style.inputStyle)

    // Flag to determine that component has been already focused before
    // Needed to prevent validation on focus switch for initial component state
    private var wasFocused = false

    internal companion object {
        val onlyDigitsRegex = "[^0-9]".toRegex()
    }

    /**
     * Make full card number validation, when focus switched to another view.
     */
    fun onFocusChanged(isFocused: Boolean) {
        if (isFocused) wasFocused = isFocused

        if (!isFocused && wasFocused) {
            val cardNumber = componentState.cardNumber.value
            handleValidationResult(cardValidator.validateCardNumber(cardNumber), false)
        }
    }

    /**
     * Update mutable state of input field value.
     * Make eager validation.
     */
    fun onCardNumberChange(text: String) = with(text.replace(onlyDigitsRegex, "")) {
        val validationResult = cardValidator.eagerValidateCardNumber(this)
        componentState.cardNumber.value = this
        paymentStateManager.cardNumber.update { this }
        handleValidationResult(validationResult, true)
    }

    private fun handleValidationResult(result: ValidationResult<CardScheme>, isEagerCheck: Boolean) = when (result) {
        is ValidationResult.Success -> with(result.value) {
            if (isEagerCheck) {
                componentState.cardScheme.value = this
                componentState.cardNumberLength.value = this.maxNumberLength
                paymentStateManager.cardScheme.update { this }
            }
            componentState.hideError()
            paymentStateManager.isCardNumberValid.update { true }
        }
        is ValidationResult.Failure -> {
            componentState.showError(R.string.cko_base_invalid_card_number_error)
            paymentStateManager.isCardNumberValid.update { false }
        }
    }

    private fun provideViewState(style: CardNumberComponentStyle): CardNumberComponentState {
        val viewState = CardNumberComponentState(inputComponentStateMapper.map(style.inputStyle))

        viewState.inputState.inputFieldState.leadingIcon.value = imageMapper.map(
            ImageStyleToDynamicImageRequest(
                style.inputStyle.inputFieldStyle.leadingIconStyle,
                snapshotFlow { viewState.cardScheme.value }.map { it.imageId }
            )
        )

        return viewState
    }

    private fun provideViewStyle(inputStyle: InputComponentStyle): InputComponentViewStyle {
        var viewStyle = inputComponentStyleMapper.map(inputStyle)
        val keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)

        viewStyle = viewStyle.copy(
            inputFieldStyle = viewStyle.inputFieldStyle.copy(
                keyboardOptions = keyboardOptions,
                visualTransformation = CardNumberTransformation(style.cardNumberSeparator, componentState.cardScheme),
                forceLTR = true
            )
        )

        return viewStyle
    }

    internal class Factory(
        private val injector: Injector,
        private val style: CardNumberComponentStyle
    ) : ViewModelProvider.Factory, InjectionClient {

        @Inject
        lateinit var subComponentProvider: Provider<CardNumberViewModelSubComponent.Builder>

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            injector.inject(this)
            return subComponentProvider.get()
                .style(style)
                .build().cardNumberViewModel as T
        }
    }
}

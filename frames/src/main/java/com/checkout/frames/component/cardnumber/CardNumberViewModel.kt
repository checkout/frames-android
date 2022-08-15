package com.checkout.frames.component.cardnumber

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.checkout.base.mapper.Mapper
import com.checkout.base.model.CardScheme
import com.checkout.frames.component.base.InputComponentState
import com.checkout.frames.di.base.InjectionClient
import com.checkout.frames.di.base.Injector
import com.checkout.frames.di.component.CardNumberViewModelSubComponent
import com.checkout.frames.style.component.CardNumberComponentStyle
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.view.InputComponentViewStyle
import com.checkout.frames.utils.CardNumberTransformation
import com.checkout.validation.api.CardValidator
import com.checkout.validation.model.ValidationResult
import javax.inject.Inject
import javax.inject.Provider

internal class CardNumberViewModel @Inject constructor(
    private val cardValidator: CardValidator,
    private val inputComponentStyleMapper: Mapper<InputComponentStyle, InputComponentViewStyle>,
    private val inputComponentStateMapper: Mapper<InputComponentStyle, InputComponentState>,
    private val style: CardNumberComponentStyle
) : ViewModel() {

    val componentState = CardNumberComponentState(inputComponentStateMapper.map(style.inputStyle))
    val componentStyle = provideViewStyle(style.inputStyle)

    /**
     * Make full card number validation, when focus switched to another view.
     */
    fun onFocusChanged(isFocused: Boolean) {
        if (!isFocused) {
            val cardNumber = componentState.cardNumber.value
            handleValidationResult(cardValidator.validateCardNumber(cardNumber))
        }
    }

    /**
     * Update mutable state of input field value.
     * Make eager validation.
     */
    fun onCardNumberChange(text: String) {
        componentState.cardNumber.value = text
        handleValidationResult(cardValidator.eagerValidateCardNumber(text))
    }

    private fun handleValidationResult(result: ValidationResult<CardScheme>) = when (result) {
        is ValidationResult.Success -> componentState.cardScheme.value = result.value
        is ValidationResult.Failure -> {
            // TODO: PIMOB-1349 - Implement error handling.
        }
    }

    private fun provideViewStyle(inputStyle: InputComponentStyle): InputComponentViewStyle {
        var viewStyle = inputComponentStyleMapper.map(inputStyle)
        val keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

        viewStyle = viewStyle.copy(
            inputFieldStyle = viewStyle.inputFieldStyle.copy(
                keyboardOptions = keyboardOptions,
                visualTransformation = CardNumberTransformation(style.cardNumberSeparator, componentState.cardScheme)
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

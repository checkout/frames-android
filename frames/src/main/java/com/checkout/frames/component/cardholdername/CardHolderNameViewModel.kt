package com.checkout.frames.component.cardholdername

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.checkout.base.mapper.Mapper
import com.checkout.frames.R
import com.checkout.frames.component.base.InputComponentState
import com.checkout.frames.di.base.InjectionClient
import com.checkout.frames.di.base.Injector
import com.checkout.frames.di.component.CardHolderNameViewModelSubComponent
import com.checkout.frames.screen.manager.PaymentStateManager
import com.checkout.frames.style.component.CardHolderNameComponentStyle
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.view.InputComponentViewStyle
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Provider

internal class CardHolderNameViewModel @Inject constructor(
    private val paymentStateManager: PaymentStateManager,
    private val inputComponentStyleMapper: Mapper<InputComponentStyle, InputComponentViewStyle>,
    private val inputComponentStateMapper: Mapper<InputComponentStyle, InputComponentState>,
    style: CardHolderNameComponentStyle
) : ViewModel() {

    val componentState = provideViewState(style)
    val componentStyle = provideViewStyle(style.inputStyle)

    // Flag to determine that component has been already focused before
    // Needed to prevent validation on focus switch for initial component state
    private var wasFocused = false

    init {
        componentState.cardHolderName.value = paymentStateManager.cardHolderName.value
    }

    /**
     * Make full card number validation, when focus switched to another view.
     */
    fun onFocusChanged(isFocused: Boolean) {
        if (isFocused) {
            wasFocused = true
        } else if (wasFocused) {
            val cardHolderName = componentState.cardHolderName.value
            handleValidationResult(cardHolderName)
        }
    }

    private fun handleValidationResult(cardHolderName: String) {
        val isValid = cardHolderName.isNotBlank() || componentStyle.isInputFieldOptional
        paymentStateManager.isCardHolderNameValid.update { isValid }

        if (wasFocused && isValid) componentState.hideError()
        else componentState.showError(R.string.cko_cardholder_name_error)
    }

    fun onCardHolderNameChange(text: String) = with(text) {
        componentState.cardHolderName.value = this
        paymentStateManager.cardHolderName.update { this }
        handleValidationResult(componentState.cardHolderName.value)
        componentState.hideError()
    }

    private fun provideViewState(style: CardHolderNameComponentStyle): CardHolderNameComponentState {
        return CardHolderNameComponentState(inputComponentStateMapper.map(style.inputStyle))
    }

    private fun provideViewStyle(inputStyle: InputComponentStyle): InputComponentViewStyle {
        var viewStyle = inputComponentStyleMapper.map(inputStyle)
        val keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)

        viewStyle = viewStyle.copy(
            inputFieldStyle = viewStyle.inputFieldStyle.copy(
                keyboardOptions = keyboardOptions, forceLTR = true
            )
        )

        return viewStyle
    }

    internal class Factory(
        private val injector: Injector,
        private val style: CardHolderNameComponentStyle,
    ) : ViewModelProvider.Factory, InjectionClient {

        @Inject
        lateinit var subComponentProvider: Provider<CardHolderNameViewModelSubComponent.Builder>

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            injector.inject(this)
            return subComponentProvider.get().style(style).build().cardHolderNameViewModel as T
        }
    }
}

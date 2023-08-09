package com.checkout.frames.component.cvv

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.checkout.base.mapper.Mapper
import com.checkout.frames.R
import com.checkout.frames.component.base.InputComponentState
import com.checkout.frames.di.base.InjectionClient
import com.checkout.frames.di.base.Injector
import com.checkout.frames.di.component.CvvViewModelSubComponent
import com.checkout.frames.screen.manager.PaymentStateManager
import com.checkout.frames.style.component.CvvComponentStyle
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.view.InputComponentViewStyle
import com.checkout.frames.utils.extensions.isValid
import com.checkout.validation.api.CardValidator
import com.checkout.validation.model.ValidationResult
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

internal class CvvViewModel @Inject constructor(
    private val paymentStateManager: PaymentStateManager,
    private val cardValidator: CardValidator,
    private val inputComponentStyleMapper: Mapper<InputComponentStyle, InputComponentViewStyle>,
    private val inputComponentStateMapper: Mapper<InputComponentStyle, InputComponentState>,
    style: CvvComponentStyle
) : ViewModel() {

    val componentState = provideViewState(style)
    val componentStyle = provideViewStyle(style.inputStyle)

    // Flag to determine that component has been already focused before
    // Needed to prevent validation on focus switch for initial component state
    private var wasFocused = false

    internal companion object {
        val onlyDigitsRegex = "[^0-9]".toRegex()
    }

    fun prepare() {
        viewModelScope.launch {
            paymentStateManager.cardScheme.collect {
                validateCvv(true)
                componentState.cvvLength.value = it.cvvLength.max()
            }
        }
    }

    /**
     * Make CVV validation, when focus switched to another view.
     */
    fun onFocusChanged(isFocused: Boolean) {
        if (isFocused) wasFocused = isFocused

        if (!isFocused && wasFocused) validateCvv(true)
    }

    /**
     * Update mutable state of input field value.
     */
    fun onCvvChange(text: String) = with(text.replace(onlyDigitsRegex, "")) {
        componentState.cvv.value = this
        paymentStateManager.cvv.update { this }
        componentState.hideError()
        if (componentState.cvv.value.length == paymentStateManager.cardScheme.value.cvvLength.min()) {
            validateCvv(false)
        } else {
            paymentStateManager.isCvvValid.update { false }
        }
    }

    /**
     * Validate cvv only when it has been already focused before
     */
    private fun validateCvv(isRequiredToHandleError: Boolean) {
        if (wasFocused) {
            val validationResult = cardValidator.validateCvv(
                componentState.cvv.value,
                paymentStateManager.cardScheme.value
            )

            paymentStateManager.isCvvValid.update { validationResult.isValid() }
            if (isRequiredToHandleError) handleValidationResult(validationResult)
        }
    }

    private fun handleValidationResult(result: ValidationResult<Unit>) = when (result) {
        is ValidationResult.Success -> componentState.hideError()
        is ValidationResult.Failure -> componentState.showError(R.string.cko_cvv_component_error)
    }

    private fun provideViewState(style: CvvComponentStyle): CvvComponentState =
        CvvComponentState(inputComponentStateMapper.map(style.inputStyle))

    private fun provideViewStyle(inputStyle: InputComponentStyle): InputComponentViewStyle {
        var viewStyle = inputComponentStyleMapper.map(inputStyle)
        val keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done)

        viewStyle = viewStyle.copy(
            inputFieldStyle = viewStyle.inputFieldStyle.copy(
                keyboardOptions = keyboardOptions,
                forceLTR = true
            )
        )

        return viewStyle
    }

    internal class Factory(
        private val injector: Injector,
        private val style: CvvComponentStyle
    ) : ViewModelProvider.Factory, InjectionClient {

        @Inject
        lateinit var subComponentProvider: Provider<CvvViewModelSubComponent.Builder>

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            injector.inject(this)
            return subComponentProvider.get()
                .style(style)
                .build().cvvViewModel as T
        }
    }
}

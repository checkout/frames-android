package com.checkout.frames.component.expirydate

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.checkout.base.mapper.Mapper
import com.checkout.base.usecase.UseCase
import com.checkout.frames.R
import com.checkout.frames.component.base.InputComponentState
import com.checkout.frames.component.expirydate.model.SmartExpiryDateValidationRequest
import com.checkout.frames.di.base.InjectionClient
import com.checkout.frames.di.base.Injector
import com.checkout.frames.di.component.ExpiryDateViewModelSubComponent
import com.checkout.frames.style.component.ExpiryDateComponentStyle
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.view.InputComponentViewStyle
import com.checkout.frames.utils.constants.EXPIRY_DATE_DIGITS_PATTERN
import com.checkout.frames.utils.constants.EXPIRY_DATE_MAXIMUM_LENGTH_FOUR
import com.checkout.frames.utils.constants.EXPIRY_DATE_MAXIMUM_LENGTH_THREE
import com.checkout.frames.utils.constants.EXPIRY_DATE_ZERO_POSITION_CHECK
import com.checkout.validation.error.ValidationError.Companion.EXPIRY_DATE_IN_PAST
import com.checkout.validation.model.ValidationResult
import javax.inject.Inject
import javax.inject.Provider

internal class ExpiryDateViewModel @Inject constructor(
    private val smartExpiryDateValidationUseCase: UseCase<SmartExpiryDateValidationRequest, ValidationResult<String>>,
    private val inputComponentStyleMapper: Mapper<InputComponentStyle, InputComponentViewStyle>,
    private val inputComponentStateMapper: Mapper<InputComponentStyle, InputComponentState>,
    private val style: ExpiryDateComponentStyle,
) : ViewModel() {

    internal companion object {
        val onlyDigitsRegex = EXPIRY_DATE_DIGITS_PATTERN.toRegex()
    }

    val componentState = provideViewState(style.inputStyle)
    val componentStyle = provideViewStyle(style.inputStyle)

    // Flag to determine that component has been already focused before
    // Needed to prevent validation on focus switch for initial component state
    private var wasFocused = false

    fun onFocusChanged(isFocused: Boolean) {
        if (isFocused) wasFocused = isFocused

        if (!isFocused && wasFocused) {
            val expiryDate = componentState.expiryDate.value
            validateExpiryDate(expiryDate, isFocused)
        }
    }

    fun onExpiryDateInputChange(inputExpiryDate: String) = with(inputExpiryDate.replace(onlyDigitsRegex, "")) {
        validateExpiryDate(this, true)
    }

    private fun updateExpiryDateMaxLength(smartLogicExpiryDateUseCase: ValidationResult.Success<String>) {
        componentState.expiryDateMaxLength.value = if (
            smartLogicExpiryDateUseCase.value.isNotEmpty() &&
            smartLogicExpiryDateUseCase.value.first() > EXPIRY_DATE_ZERO_POSITION_CHECK
        ) EXPIRY_DATE_MAXIMUM_LENGTH_THREE else EXPIRY_DATE_MAXIMUM_LENGTH_FOUR
    }

    private fun validateExpiryDate(expiryDate: String, isFocused: Boolean) {
        val smartExpiryDateValidationRequest = SmartExpiryDateValidationRequest(isFocused, expiryDate)

        handleValidationResult(smartExpiryDateValidationUseCase.execute(smartExpiryDateValidationRequest), isFocused)
    }

    private fun handleValidationResult(result: ValidationResult<String>, isFocused: Boolean) = when (result) {
        is ValidationResult.Success -> {
            updateExpiryDateMaxLength(result)
            componentState.expiryDate.value = result.value
            componentState.hideError()
        }

        is ValidationResult.Failure -> {
            if (isFocused && result.error.errorCode == EXPIRY_DATE_IN_PAST)
                componentState.showError(R.string.cko_base_invalid_past_expiry_date_error)
            else
                componentState.showError(R.string.cko_base_invalid_expiry_date_error)
        }
    }

    private fun provideViewState(inputStyle: InputComponentStyle): ExpiryDateComponentState {
        val viewState = ExpiryDateComponentState(inputComponentStateMapper.map(inputStyle))
        viewState.expiryDateMaxLength.value = EXPIRY_DATE_MAXIMUM_LENGTH_FOUR

        return viewState
    }

    private fun provideViewStyle(inputStyle: InputComponentStyle): InputComponentViewStyle {
        var viewStyle = inputComponentStyleMapper.map(inputStyle)
        val keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)

        viewStyle = viewStyle.copy(
            inputFieldStyle = viewStyle.inputFieldStyle.copy(
                keyboardOptions = keyboardOptions,
                visualTransformation = ExpiryDateVisualTransformation(),
                forceLTR = true
            )
        )

        return viewStyle
    }

    internal class Factory(
        private val injector: Injector,
        private val style: ExpiryDateComponentStyle,
    ) : ViewModelProvider.Factory, InjectionClient {

        @Inject
        lateinit var subComponentProvider: Provider<ExpiryDateViewModelSubComponent.Builder>

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            injector.inject(this)
            return subComponentProvider.get()
                .style(style)
                .build().expiryDateViewModel as T
        }
    }
}

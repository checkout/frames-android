package com.checkout.frames.component.expirydate

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.checkout.base.mapper.Mapper
import com.checkout.frames.base.validation.ValidateResult
import com.checkout.frames.component.base.InputComponentState
import com.checkout.frames.component.expirydate.usecase.ValidateExpiryDateUseCase
import com.checkout.frames.di.base.InjectionClient
import com.checkout.frames.di.base.Injector
import com.checkout.frames.di.component.ExpiryDateViewModelSubComponent
import com.checkout.frames.style.component.ExpiryDateComponentStyle
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.view.InputComponentViewStyle
import com.checkout.frames.utils.constants.InputFieldConstants.EXPIRY_DATE_MAXIMUM_LENGTH
import com.checkout.frames.view.InputFieldState
import com.checkout.tokenization.model.ExpiryDate
import com.checkout.validation.api.CardValidator
import com.checkout.validation.model.ValidationResult
import javax.inject.Inject
import javax.inject.Provider

@SuppressWarnings("UnusedPrivateMember")
internal class ExpiryDateViewModel @Inject constructor(
    private val cardValidator: CardValidator,
    private val inputComponentStyleMapper: Mapper<InputComponentStyle, InputComponentViewStyle>,
    private val inputComponentStateMapper: Mapper<InputComponentStyle, InputComponentState>,
    private val style: ExpiryDateComponentStyle,
) : ViewModel() {

    val componentState = provideViewState(style.inputStyle)
    val componentStyle = provideViewStyle(style.inputStyle)

    // Flag to determine that component has been already focused before
    // Needed to prevent validation on focus switch for initial component state
    private var wasFocused = false

    fun onFocusChanged(isFocused: Boolean) {
        if (isFocused) wasFocused = isFocused

        if (!isFocused && wasFocused) {
            val expiryDate = componentState.expiryDate.value
            validateExpiryDateOnFocusChanged(expiryDate)
        }
    }

    private fun validateExpiryDateOnFocusChanged(expiryDate: String) {
        if (expiryDate.length >= EXPIRY_DATE_MAXIMUM_LENGTH) {
            handleValidationResult(cardValidator.validateExpiryDate(expiryDate.take(2), expiryDate.takeLast(2)))
        } else {
            // TODO: PIMOB-1401 - Implement error handling for expiry date.
        }
    }

    fun onExpiryDateInputChange(text: String) {
        val strBuilder = StringBuilder()
        strBuilder.append(text)

        when (val validateExpiryDateUseCase = ValidateExpiryDateUseCase().execute(strBuilder)) {
            is ValidateResult.Success -> {
                componentState.inputState.inputFieldState.text.value = validateExpiryDateUseCase.value
            }
            is ValidateResult.Failure -> {
                componentState.inputState.inputFieldState.text.value = validateExpiryDateUseCase.data
                // TODO: PIMOB-1401 - Implement error handling for expiry date.
            }
        }
    }

    private fun handleValidationResult(result: ValidationResult<ExpiryDate>) = when (result) {
        is ValidationResult.Success -> with(result.value) { }
        is ValidationResult.Failure -> {
            // TODO: PIMOB-1401 - Implement error handling for expiry date.
        }
    }

    private fun provideViewState(inputStyle: InputComponentStyle): ExpiryDateComponentState {
        var viewState = ExpiryDateComponentState(inputComponentStateMapper.map(inputStyle))
        val inputFieldState = InputFieldState(maxLength = mutableStateOf(EXPIRY_DATE_MAXIMUM_LENGTH))

        viewState = viewState.copy(
            inputState = viewState.inputState.copy(
                inputFieldState = inputFieldState
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
                visualTransformation = ExpiryDateVisualTransformation()
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

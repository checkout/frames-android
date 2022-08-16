package com.checkout.frames.component.expirydate

import android.util.Log
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.checkout.base.mapper.Mapper
import com.checkout.frames.component.base.InputComponentState
import com.checkout.frames.di.base.InjectionClient
import com.checkout.frames.di.base.Injector
import com.checkout.frames.di.component.ExpiryDateViewModelSubComponent
import com.checkout.frames.style.component.ExpiryDateComponentStyle
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.view.InputComponentViewStyle
import com.checkout.frames.utils.constants.InputFieldConstants
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

    val componentState = ExpiryDateComponentState(inputComponentStateMapper.map(style.inputStyle))
    val componentStyle = provideViewStyle(style.inputStyle)

    fun onExpiryDateInputChange(text: String) {
        componentState.inputState.inputFieldState.text.value = text
        handleValidationResult(cardValidator.validateExpiryDate(text, text))
    }

    // TODO: PIMOB-1393 - Implement smart logic for expiry date.
    private fun handleValidationResult(validateExpiryDate: ValidationResult<ExpiryDate>) {
     Log.d("System out", "handleValidationResult $validateExpiryDate")
    }

    private fun provideViewStyle(inputStyle: InputComponentStyle): InputComponentViewStyle {
        var viewStyle = inputComponentStyleMapper.map(inputStyle)
        val keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

        viewStyle = viewStyle.copy(
            inputFieldStyle = viewStyle.inputFieldStyle.copy(
                keyboardOptions = keyboardOptions,
                visualTransformation = ExpiryDateVisualTransformation(),
                maxLength = InputFieldConstants.EXPIRY_DATE_MAX_LENGTH
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

package com.checkout.frames.cvvinputfield.viewmodel

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import com.checkout.base.mapper.Mapper
import com.checkout.frames.cvvinputfield.CVVInputFieldState
import com.checkout.frames.cvvinputfield.models.CVVComponentConfig
import com.checkout.frames.style.component.base.InputFieldStyle
import com.checkout.frames.style.view.InputFieldViewStyle
import com.checkout.frames.view.InputFieldState
import com.checkout.validation.api.CVVComponentValidator
import com.checkout.validation.model.ValidationResult

internal class CVVInputFieldViewModel internal constructor(
    val inputFieldStateMapper: Mapper<InputFieldStyle, InputFieldState>,
    val inputFieldStyleMapper: Mapper<InputFieldStyle, InputFieldViewStyle>,
    val cvvComponentConfig: CVVComponentConfig,
    val cvvComponentValidator: CVVComponentValidator,
) : ViewModel() {

    val cvvInputFieldState = provideViewState(cvvComponentConfig.cvvInputFieldStyle)
    val cvvInputFieldStyle = provideViewStyle(cvvComponentConfig.cvvInputFieldStyle)

    /**
     * Update mutable state of input field value.
     */
    fun onCvvChange(text: String) = with(text.isDigitsOnly()) {
        cvvInputFieldState.cvv.value = text
        validate()
    }

    private fun validate() {
        with(cvvComponentConfig) {
            when (cvvComponentValidator.validate(cvvInputFieldState.cvv.value, cardScheme)) {
                is ValidationResult.Success -> onCVVValueChange(true)
                is ValidationResult.Failure -> onCVVValueChange(false)
            }
        }
    }

    private fun provideViewStyle(inputStyle: InputFieldStyle): InputFieldViewStyle =
        inputFieldStyleMapper.map(inputStyle).copy(
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            ),
            forceLTR = false,
        )

    private fun provideViewState(inputFieldStyle: InputFieldStyle): CVVInputFieldState =
        CVVInputFieldState(inputFieldStateMapper.map(inputFieldStyle)).apply {
            cvvLength.value = cvvComponentConfig.cardScheme.cvvLength.max()
        }
}

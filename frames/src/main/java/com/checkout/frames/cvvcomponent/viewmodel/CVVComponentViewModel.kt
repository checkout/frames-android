package com.checkout.frames.cvvcomponent.viewmodel

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import com.checkout.base.mapper.Mapper
import com.checkout.frames.cvvcomponent.models.CVVComponentConfig
import com.checkout.frames.style.component.base.InputFieldStyle
import com.checkout.frames.style.view.InputFieldViewStyle
import com.checkout.frames.view.InputFieldState
import com.checkout.validation.api.CardValidator

@Suppress("UnusedPrivateMember")
internal class CVVComponentViewModel internal constructor(
    val inputFieldStateMapper: Mapper<InputFieldStyle, InputFieldState>,
    val inputFieldStyleMapper: Mapper<InputFieldStyle, InputFieldViewStyle>,
    val cvvComponentConfig: CVVComponentConfig,
    val cardValidator: CardValidator,
) : ViewModel() {

    val cvvInputFieldState = inputFieldStateMapper.map(cvvComponentConfig.cvvInputFieldStyle)
    val cvvInputFieldStyle = provideViewStyle(cvvComponentConfig.cvvInputFieldStyle)

    /**
     * Update mutable state of input field value.
     */
    fun onCvvChange(text: String) {
        if (text.isDigitsOnly()) {
            cvvInputFieldState.text.value = text
        }
    }

    private fun provideViewStyle(inputStyle: InputFieldStyle): InputFieldViewStyle =
        inputFieldStyleMapper.map(inputStyle).copy(
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
            ),
            forceLTR = false
        )
}

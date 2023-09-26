package com.checkout.frames.cvvcomponent.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.checkout.base.mapper.Mapper
import com.checkout.frames.cvvcomponent.models.CVVComponentConfig
import com.checkout.frames.style.component.base.InputFieldStyle
import com.checkout.frames.style.view.InputFieldViewStyle
import com.checkout.frames.view.InputFieldState
import com.checkout.validation.api.CardValidator

@Suppress("UNCHECKED_CAST")
internal class CVVComponentViewModelFactory(
    private val config: CVVComponentConfig,
    private val cardValidator: CardValidator,
    private val inputFieldStateMapper: Mapper<InputFieldStyle, InputFieldState>,
    private val inputFieldStyleMapper: Mapper<InputFieldStyle, InputFieldViewStyle>,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CVVComponentViewModel::class.java)) {
            return CVVComponentViewModel(
                inputFieldStateMapper,
                inputFieldStyleMapper,
                cvvComponentConfig = config,
                cardValidator = cardValidator,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

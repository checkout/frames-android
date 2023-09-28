package com.checkout.frames.cvvinputfield

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.checkout.CVVComponentValidatorFactory
import com.checkout.frames.cvvinputfield.models.CVVComponentConfig
import com.checkout.frames.cvvinputfield.viewmodel.CVVInputFieldViewModel
import com.checkout.frames.cvvinputfield.viewmodel.CVVInputFieldViewModelFactory
import com.checkout.frames.mapper.ContainerStyleToModifierMapper
import com.checkout.frames.mapper.ImageStyleToComposableImageMapper
import com.checkout.frames.mapper.InputFieldStyleToInputFieldStateMapper
import com.checkout.frames.mapper.InputFieldStyleToViewStyleMapper
import com.checkout.frames.mapper.TextLabelStyleToViewStyleMapper
import com.checkout.frames.view.InputField

@Composable
internal fun CVVInputField(
    config: CVVComponentConfig,
) {

    val viewModel: CVVInputFieldViewModel = viewModel(
        factory = CVVInputFieldViewModelFactory(
            config = config,
            cvvComponentValidator = CVVComponentValidatorFactory.create(),
            inputFieldStateMapper = InputFieldStyleToInputFieldStateMapper(ImageStyleToComposableImageMapper()),
            inputFieldStyleMapper = InputFieldStyleToViewStyleMapper(
                TextLabelStyleToViewStyleMapper(
                    ContainerStyleToModifierMapper()
                )
            )
        )
    )

    InputField(viewModel.cvvInputFieldStyle, viewModel.cvvInputFieldState.inputFieldState, viewModel::onCvvChange)
}

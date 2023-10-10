package com.checkout.frames.cvvinputfield

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import java.util.Random

@Composable
internal fun CVVInputField(
    config: CVVComponentConfig,
    onValueChange: (String) -> Unit,
) {
    val uniqueKey = remember { "${System.currentTimeMillis()}_${Random().nextInt()}" }
    val viewModel: CVVInputFieldViewModel = viewModel(
        key = uniqueKey,
        factory = CVVInputFieldViewModelFactory(
            config = config,
            cvvComponentValidator = CVVComponentValidatorFactory.create(),
            inputFieldStateMapper = InputFieldStyleToInputFieldStateMapper(ImageStyleToComposableImageMapper()),
            inputFieldStyleMapper = InputFieldStyleToViewStyleMapper(
                TextLabelStyleToViewStyleMapper(
                    ContainerStyleToModifierMapper(),
                ),
            ),
        ),
    )

    onValueChange(viewModel.cvvInputFieldState.inputFieldState.text.value)

    InputField(viewModel.cvvInputFieldStyle, viewModel.cvvInputFieldState.inputFieldState, viewModel::onCvvChange)
}

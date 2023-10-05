package com.checkout.example.frames.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.checkout.base.model.CardScheme
import com.checkout.base.model.Environment
import com.checkout.example.frames.styling.CustomCVVInputFieldStyle
import com.checkout.example.frames.ui.utils.PUBLIC_KEY
import com.checkout.example.frames.ui.viewmodel.CVVTokenizationViewModel
import com.checkout.frames.cvvinputfield.CVVComponentApiFactory
import com.checkout.frames.cvvinputfield.api.CVVComponentApi
import com.checkout.frames.cvvinputfield.api.CVVComponentMediator
import com.checkout.frames.cvvinputfield.models.CVVComponentConfig
import com.checkout.frames.cvvinputfield.style.DefaultCVVInputFieldStyle
import com.checkout.frames.model.Margin
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.InputFieldStyle

@Suppress("MagicNumber", "LongMethod")
@Composable
fun CVVTokenizationScreen(navController: NavHostController) {
    val cvvTokenizationViewModel: CVVTokenizationViewModel = viewModel()
    val cvvComponentApi = CVVComponentApiFactory.create(PUBLIC_KEY, Environment.SANDBOX, LocalContext.current)

    val visaMediator = createMediator(
        cvvComponentApi = cvvComponentApi,
        schemeValue = "Visa",
        inputFieldStyle = DefaultCVVInputFieldStyle.create().copy(
            containerStyle = ContainerStyle(width = 290, margin = Margin(end = 10))
        ),
        enteredVisaCVVUpdated = cvvTokenizationViewModel.isEnteredVisaCVVValid
    )

    val maestroSecond = createMediator(
        cvvComponentApi = cvvComponentApi,
        schemeValue = "Maestro",
        inputFieldStyle = CustomCVVInputFieldStyle.create(),
        enteredVisaCVVUpdated = cvvTokenizationViewModel.isEnteredMaestroCVVValid
    )

    LoadCVVComponentsContents(navController, cvvTokenizationViewModel, visaMediator, maestroSecond)
}

fun createMediator(
    cvvComponentApi: CVVComponentApi,
    schemeValue: String,
    inputFieldStyle: InputFieldStyle,
    enteredVisaCVVUpdated: MutableState<Boolean>,
): CVVComponentMediator {
    val cvvComponentConfig = CVVComponentConfig(
        cardScheme = CardScheme.fromString(cardSchemeValue = schemeValue), onCVVValueChange = { isValidCVV ->
            enteredVisaCVVUpdated.value = isValidCVV
        }, cvvInputFieldStyle = inputFieldStyle
    )

    return cvvComponentApi.createComponentMediator(cvvComponentConfig)
}

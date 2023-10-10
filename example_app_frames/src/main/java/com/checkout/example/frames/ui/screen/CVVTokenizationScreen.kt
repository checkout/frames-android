package com.checkout.example.frames.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.checkout.base.model.CardScheme
import com.checkout.base.model.Environment
import com.checkout.example.frames.paymentformstyling.PaymentFormConstants
import com.checkout.example.frames.styling.CustomCVVInputFieldStyle
import com.checkout.example.frames.ui.utils.PUBLIC_KEY_CVV_TOKENIZATION
import com.checkout.example.frames.ui.viewmodel.CVVTokenizationViewModel
import com.checkout.frames.cvvinputfield.CVVComponentApiFactory
import com.checkout.frames.cvvinputfield.api.CVVComponentApi
import com.checkout.frames.cvvinputfield.api.CVVComponentMediator
import com.checkout.frames.cvvinputfield.models.CVVComponentConfig
import com.checkout.frames.cvvinputfield.style.DefaultCVVInputFieldStyle
import com.checkout.frames.model.CornerRadius
import com.checkout.frames.model.Padding
import com.checkout.frames.model.Shape
import com.checkout.frames.model.font.Font
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.InputFieldStyle
import com.checkout.frames.style.component.base.TextStyle

@Suppress("MagicNumber")
@Composable
fun CVVTokenizationScreen(navController: NavHostController) {
    val cvvTokenizationViewModel: CVVTokenizationViewModel = viewModel()

    val cvvComponentApi = CVVComponentApiFactory.create(
        publicKey = PUBLIC_KEY_CVV_TOKENIZATION,
        environment = Environment.SANDBOX,
        context = LocalContext.current,
    )

    val visaMediator = createMediator(
        cvvComponentApi = cvvComponentApi,
        schemeValue = "Visa",
        inputFieldStyle = DefaultCVVInputFieldStyle.create().copy(
            containerStyle = ContainerStyle(width = 250, padding = Padding(end = 10)),
        ),
        enteredVisaCVVUpdated = cvvTokenizationViewModel.isEnteredVisaCVVValid,
    )

    val maestroMediator = createMediator(
        cvvComponentApi = cvvComponentApi,
        schemeValue = "Maestro",
        inputFieldStyle = CustomCVVInputFieldStyle.create(),
        enteredVisaCVVUpdated = cvvTokenizationViewModel.isEnteredMaestroCVVValid,
    )

    val amexMediator = createMediator(
        cvvComponentApi = cvvComponentApi,
        schemeValue = "AMERICAN_EXPRESS",
        inputFieldStyle = CustomCVVInputFieldStyle.create().copy(
            containerStyle = ContainerStyle(
                width = 250,
                color = 0XFF24302D,
                shape = Shape.Rectangle,
                padding = Padding(end = 2),
                cornerRadius = CornerRadius(9),
            ),
            textStyle = TextStyle(16, color = 0XFF00CC2D, font = Font.Serif),
        ),
        enteredVisaCVVUpdated = cvvTokenizationViewModel.isEnteredAmexCVVValid,
    )

    val unknownMediator = createMediator(
        cvvComponentApi = cvvComponentApi,
        schemeValue = "unknown",
        inputFieldStyle = CustomCVVInputFieldStyle.create().copy(
            containerStyle = ContainerStyle(
                width = 250,
                padding = Padding(end = 2),
                color = PaymentFormConstants.backgroundColor,
                shape = Shape.Circle,
                cornerRadius = CornerRadius(9),
            ),
        ),
    )

    LoadCVVComponentsContents(
        navController = navController,
        cvvTokenizationViewModel = cvvTokenizationViewModel,
        visaMediator = visaMediator,
        maestroMediator = maestroMediator,
        amexMediator = amexMediator,
        unknownMediator = unknownMediator,
    )
}

fun createMediator(
    cvvComponentApi: CVVComponentApi,
    schemeValue: String,
    inputFieldStyle: InputFieldStyle,
    enteredVisaCVVUpdated: MutableState<Boolean> = mutableStateOf(true),
): CVVComponentMediator {
    val cvvComponentConfig = CVVComponentConfig(
        cardScheme = CardScheme.fromString(cardSchemeValue = schemeValue),
        onCVVValueChange = { isValidCVV ->
            enteredVisaCVVUpdated.value = isValidCVV
        },
        cvvInputFieldStyle = inputFieldStyle,
    )

    return cvvComponentApi.createComponentMediator(cvvComponentConfig)
}

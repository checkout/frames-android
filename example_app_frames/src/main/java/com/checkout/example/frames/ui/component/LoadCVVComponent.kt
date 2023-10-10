package com.checkout.example.frames.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.checkout.example.frames.ui.extension.showAlertDialog
import com.checkout.frames.R
import com.checkout.frames.cvvinputfield.api.CVVComponentMediator
import com.checkout.tokenization.model.CVVTokenizationResultHandler

@Composable
fun LoadCVVComponent(
    cvvComponentMediator: CVVComponentMediator,
    isEnteredCVVValid: MutableState<Boolean> = mutableStateOf(true),
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        cvvComponentMediator.CVVComponent()

        CustomButton(isCVVValid = isEnteredCVVValid) {
            cvvComponentMediator.createToken { result ->
                when (result) {
                    is CVVTokenizationResultHandler.Success -> {
                        val tokenDetails = result.tokenDetails
                        context.showAlertDialog(
                            title = context.getString(R.string.token_generated),
                            message = tokenDetails.token,
                        )
                    }

                    is CVVTokenizationResultHandler.Failure -> {
                        val errorMessage = result.errorMessage
                        context.showAlertDialog(
                            title = context.getString(R.string.token_generated_failed),
                            message = errorMessage,
                        )
                    }
                }
            }
        }
    }
}

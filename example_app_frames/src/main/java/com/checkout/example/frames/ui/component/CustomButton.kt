package com.checkout.example.frames.ui.component

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color

@Suppress("MagicNumber")
@Composable
fun CustomButton(
    isCVVValid: MutableState<Boolean>,
    buttonClick: () -> Unit,
) {
    Button(
        onClick = {
            buttonClick.invoke()
        },
        enabled = isCVVValid.value,
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = Color(0xFF0B5FF0),
            disabledContentColor = Color.Gray,
        ),
    ) {
        Text(text = "Pay")
    }
}

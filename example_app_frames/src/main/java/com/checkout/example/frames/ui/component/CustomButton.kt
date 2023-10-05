package com.checkout.example.frames.ui.component

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color

@Composable
fun CustomButton(isCVVValid: MutableState<Boolean>) {
    Button(
        onClick = {
            // TODO: Tokenization
        }, enabled = isCVVValid.value,
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White, containerColor = Color.Green, disabledContentColor = Color.Gray
        )
    ) {
        Text(text = "Pay")
    }
}

package com.checkout.frames.component.expirydate

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.checkout.frames.component.base.InputComponent
import com.checkout.frames.di.base.Injector
import com.checkout.frames.di.injector.FramesInjector
import com.checkout.frames.model.Margin
import com.checkout.frames.model.font.Font
import com.checkout.frames.style.component.ExpiryDateComponentStyle
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.style.component.base.TextStyle
import com.checkout.frames.style.component.base.InputFieldStyle
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.InputFieldIndicatorStyle

@Composable
internal fun ExpiryDateComponent(
    style: ExpiryDateComponentStyle,
    injector: Injector,
) {
    val viewModel: ExpiryDateViewModel = viewModel(
        factory = ExpiryDateViewModel.Factory(injector, style)
    )

    InputComponent(
        style = viewModel.componentStyle,
        state = viewModel.componentState.inputState,
        onFocusChanged = viewModel::onFocusChanged,
        onValueChange = viewModel::onExpiryDateInputChange
    )
}

@SuppressWarnings("LongMethod", "MagicNumber")
@Preview(showBackground = true)
@Composable
private fun ExpiryDateComponentPreview() {
    val style = InputComponentStyle(
        titleStyle = TextLabelStyle(
            "Expiry Date",
            textStyle = TextStyle(size = 16, color = 0xFF141414, font = Font.SansSerif, maxLines = 1),
        ),
        subtitleStyle = TextLabelStyle(
            "Format is MM/YY",
            textStyle = TextStyle(size = 12, color = 0xFF636363, font = Font.SansSerif, maxLines = 2),
        ),
        inputFieldStyle = InputFieldStyle(
            textStyle = TextStyle(size = 16, color = 0xFF141414, font = Font.SansSerif, maxLines = 1),
            placeholderText = "__ / __",
            placeholderStyle = TextStyle(size = 16, color = 0x80636363, font = Font.SansSerif, maxLines = 1),
            containerStyle = ContainerStyle(margin = Margin(top = 8, bottom = 8)),
            indicatorStyle = InputFieldIndicatorStyle.Border(
                unfocusedBorderColor = 0xFF8A8A8A,
                focusedBorderColor = 0xFF0B5FF0,
                errorBorderColor = 0xFFAD283E
            )
        )
    )
    Column(modifier = Modifier.padding(20.dp)) {
        Text(
            text = "Payment details",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 20.dp),
            color = Color(0xFF000000),
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif
        )

        ExpiryDateComponent(
            ExpiryDateComponentStyle(
                style
            ),
            FramesInjector.create()
        )
        OutlinedTextField(value = "", onValueChange = {}, modifier = Modifier.padding(top = 20.dp))
    }
}

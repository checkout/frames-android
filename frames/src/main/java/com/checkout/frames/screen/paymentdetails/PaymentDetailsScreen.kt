package com.checkout.frames.screen.paymentdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.checkout.frames.di.base.Injector
import com.checkout.frames.style.screen.PaymentDetailsStyle
import com.checkout.frames.utils.constants.PaymentDetailsScreenConstants

@SuppressWarnings("MagicNumber")
@Composable
internal fun PaymentDetailsScreen(
    style: PaymentDetailsStyle,
    injector: Injector
) {
    val viewModel: PaymentDetailsViewModel = viewModel(
        factory = PaymentDetailsViewModel.Factory(injector, style)
    )

    Column(modifier = Modifier.padding(PaymentDetailsScreenConstants.padding.dp)) {

        // TODO: Replace with header component
        Text(
            text = "Payment details",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 20.dp, top = 20.dp),
            color = Color(0xFF1C1B1F),
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily.SansSerif
        )

        viewModel.componentProvider.CardNumber(style = style.cardNumberStyle)
    }
}

package com.checkout.frames.screen.paymentdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.checkout.frames.di.base.Injector
import com.checkout.frames.screen.navigation.Screen
import com.checkout.frames.style.screen.PaymentDetailsStyle
import com.checkout.frames.utils.constants.PaymentDetailsScreenConstants

@SuppressWarnings("MagicNumber")
@Composable
internal fun PaymentDetailsScreen(
    style: PaymentDetailsStyle,
    injector: Injector,
    navController: NavController
) {
    val viewModel: PaymentDetailsViewModel = viewModel(
        factory = PaymentDetailsViewModel.Factory(injector, style)
    )

    Column(
        modifier = Modifier
            .padding(PaymentDetailsScreenConstants.padding.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {

        // TODO: Replace with header component
        Text(
            text = "Payment details",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 8.dp, top = 20.dp),
            color = Color(0xFF1C1B1F),
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily.SansSerif
        )

        viewModel.componentProvider.CardScheme(style = style.cardSchemeStyle)

        viewModel.componentProvider.CardNumber(style = style.cardNumberStyle)

        Spacer(modifier = Modifier.padding(top = 24.dp))

        viewModel.componentProvider.ExpiryDate(style = style.expiryDateComponentStyle)

        Spacer(modifier = Modifier.padding(top = 24.dp))

        viewModel.componentProvider.Cvv(style = style.cvvComponentStyle)

        Spacer(modifier = Modifier.padding(top = 24.dp))

        viewModel.componentProvider.AddressSummary(style.addressSummaryComponentStyle) {
            navController.navigate(Screen.BillingFormScreen.route)
        }

        Spacer(modifier = Modifier.padding(top = 32.dp))

        viewModel.componentProvider.PayButton(style = style.payButtonComponentStyle)
    }
}

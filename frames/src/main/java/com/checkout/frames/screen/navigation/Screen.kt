package com.checkout.frames.screen.navigation

internal sealed class Screen(val route: String) {
    object PaymentDetails : Screen(PAYMENT_DETAILS_SCREEN)
}

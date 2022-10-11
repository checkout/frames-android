package com.checkout.frames.screen.navigation

internal sealed class Screen(val route: String) {
    object PaymentDetails : Screen(PAYMENT_DETAILS_SCREEN)
    object CountryPicker : Screen(COUNTRY_PICKER_SCREEN)
    object BillingFormDetails : Screen(BILLING_FORM_DETAILS_SCREEN)
}

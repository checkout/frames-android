package com.checkout.example.frames.navigation

sealed class Screen(val route: String) {
    object Home : Screen(HOME_SCREEN)

    object DefaultUI : Screen(DEFAULT_UI_SCREEN)

    object CustomThemingUI : Screen(CUSTOM_THEMING_UI_SCREEN)

    object CustomUI : Screen(CUSTOM_UI_SCREEN)
}

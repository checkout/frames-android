package com.checkout.example.frames.navigation

sealed class Screen(val route: String) {
    object Home : Screen(HOME_SCREEN)

    object DefaultUI : Screen(DEFAULT_UI_SCREEN)
}

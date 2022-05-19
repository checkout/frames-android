package com.checkout.android_sdk.Utils

internal interface CheckoutThemeInterface {
    fun getColorPrimaryProperty(): Map<String, Any?>
    fun getColorAccentProperty(): Map<String, Any?>
    fun getColorButtonNormalProperty(): Map<String, Any?>
    fun getTextColorPrimaryProperty(): Map<String, Any?>
    fun getColorControlActivatedProperty(): Map<String, Any?>
    fun getColorControlNormalProperty(): Map<String, Any?>
}
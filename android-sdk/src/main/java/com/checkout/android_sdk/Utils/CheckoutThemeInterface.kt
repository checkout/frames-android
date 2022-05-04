package com.checkout.android_sdk.Utils

internal interface CheckoutThemeInterface {
    fun getColorPrimaryProperty(): MutableMap<String, Any?>
    fun getColorAccentProperty(): MutableMap<String, Any?>
    fun getColorButtonNormalProperty(): MutableMap<String, Any?>
    fun getTextColorPrimaryProperty(): MutableMap<String, Any?>
    fun getColorControlActivatedProperty(): MutableMap<String, Any?>
    fun getColorControlNormalProperty(): MutableMap<String, Any?>
}
package com.checkout.android_sdk.Utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import com.checkout.android_sdk.R

internal class CheckoutTheme(val context: Context) : CheckoutThemeInterface {
    private var colorPrimary: MutableMap<String, Any?>
    private var colorAccent: MutableMap<String, Any?>
    private var colorButtonNormal: MutableMap<String, Any?>
    private var colorControlNormal: MutableMap<String, Any?>
    private var textColorPrimary: MutableMap<String, Any?>
    private var colorControlActivated: MutableMap<String, Any?>

    init {
        colorPrimary = geThemeColorResource(R.attr.colorPrimary)
        colorAccent = geThemeColorResource(R.attr.colorAccent)
        colorButtonNormal = geThemeColorResource(R.attr.colorButtonNormal)
        colorControlNormal = geThemeColorResource(R.attr.colorControlNormal)
        textColorPrimary = geThemeColorResource(android.R.attr.textColorPrimary)
        colorControlActivated = geThemeColorResource(R.attr.colorControlActivated)
    }

    private fun geThemeColorResource(colorAttribute: Int): MutableMap<String, Any?> {
        val colorValues = mutableMapOf<String, Any?>()

        val typedValue = TypedValue()
        val theme: Resources.Theme = context.theme
        theme.resolveAttribute(colorAttribute, typedValue, true)
        val color = typedValue.data

        color.let {
            colorValues["alpha"] = color shr 24 and 0xFF
            colorValues["red"] = color shr 16 and 0xFF
            colorValues["green"] = color shr 8 and 0xFF
            colorValues["blue"] = color and 0xFF
        }
        return colorValues
    }

    override fun getColorPrimaryProperty(): MutableMap<String, Any?> {
        return colorPrimary
    }

    override fun getColorAccentProperty(): MutableMap<String, Any?> {
        return colorAccent
    }

    override fun getColorButtonNormalProperty(): MutableMap<String, Any?> {
        return colorButtonNormal
    }

    override fun getTextColorPrimaryProperty(): MutableMap<String, Any?> {
        return textColorPrimary
    }

    override fun getColorControlActivatedProperty(): MutableMap<String, Any?> {
        return colorControlActivated
    }

    override fun getColorControlNormalProperty(): MutableMap<String, Any?> {
        return colorControlNormal
    }
}
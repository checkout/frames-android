package com.checkout.android_sdk.Utils

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import com.checkout.android_sdk.R

internal class CheckoutTheme(private val context: Context) : CheckoutThemeInterface {
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
        val typedValue = TypedValue()
        context.theme.resolveAttribute(colorAttribute, typedValue, true)
        val color = typedValue.data

        return mutableMapOf(
            "alpha" to Color.alpha(color),
            "red" to Color.red(color),
            "green" to Color.green(color),
            "blue" to Color.blue(color)
        )
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
package com.checkout.android_sdk.Utils

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import com.checkout.android_sdk.R

internal class CheckoutTheme(private val context: Context) : CheckoutThemeInterface {
    private var colorPrimary: Map<String, Any?>
    private var colorAccent: Map<String, Any?>
    private var colorButtonNormal: Map<String, Any?>
    private var colorControlNormal: Map<String, Any?>
    private var textColorPrimary: Map<String, Any?>
    private var colorControlActivated: Map<String, Any?>

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

    override fun getColorPrimaryProperty(): Map<String, Any?> {
        return colorPrimary
    }

    override fun getColorAccentProperty(): Map<String, Any?> {
        return colorAccent
    }

    override fun getColorButtonNormalProperty(): Map<String, Any?> {
        return colorButtonNormal
    }

    override fun getTextColorPrimaryProperty(): Map<String, Any?> {
        return textColorPrimary
    }

    override fun getColorControlActivatedProperty(): Map<String, Any?> {
        return colorControlActivated
    }

    override fun getColorControlNormalProperty(): Map<String, Any?> {
        return colorControlNormal
    }
}
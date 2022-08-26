package com.checkout.frames.model.font

import androidx.annotation.FontRes

public sealed class Font {
    /**
     * The platform default font.
     */
    public object Default : Font()

    /**
     * The formal text inputStyle for scripts.
     *
     * @sample androidx.compose.ui.text.samples.FontFamilySerifSample
     *
     * See [CSS serif](https://www.w3.org/TR/css-fonts-3/#serif)
     */
    public object Serif : Font()

    /**
     * Font family with low contrast and plain stroke endings.
     *
     * @sample androidx.compose.ui.text.samples.FontFamilySansSerifSample
     *
     * See [CSS sans-serif](https://www.w3.org/TR/css-fonts-3/#sans-serif)
     */
    public object SansSerif : Font()

    /**
     * Font family where glyphs have the same fixed width.
     *
     * @sample androidx.compose.ui.text.samples.FontFamilyMonospaceSample
     *
     * See [CSS monospace](https://www.w3.org/TR/css-fonts-3/#monospace)
     */
    public object Monospace : Font()

    /**
     * Cursive, hand-written like font family.
     *
     * If the device doesn't support this font family, the system will fallback to the
     * default font.
     *
     * @sample androidx.compose.ui.text.samples.FontFamilyCursiveSample
     *
     * See [CSS cursive](https://www.w3.org/TR/css-fonts-3/#cursive)
     */
    public object Cursive : Font()

    /**
     * Custom font family.
     *
     * @param normalFont integer font resource id for normal weight.
     * @param normalItalicFont integer font resource id for normal italic weight.
     * @param lightFont integer font resource id for light weight.
     * @param mediumFont integer font resource id for medium weight.
     * @param semiBold integer font resource id for semi bold weight.
     * @param boldFont integer font resource id for bold weight.
     * @param extraBoldFont integer font resource id for extra bold weight.
     */
    public data class Custom(
        @FontRes
        val normalFont: Int,
        @FontRes
        val normalItalicFont: Int? = null,
        @FontRes
        val lightFont: Int? = null,
        @FontRes
        val mediumFont: Int? = null,
        @FontRes
        val semiBold: Int? = null,
        @FontRes
        val boldFont: Int? = null,
        @FontRes
        val extraBoldFont: Int? = null,
    ) : Font()
}

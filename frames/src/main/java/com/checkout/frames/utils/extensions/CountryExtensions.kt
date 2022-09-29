package com.checkout.frames.utils.extensions

import com.checkout.base.model.Country

/**
 * The regional indicator symbols are a set of 26 alphabetic Unicode characters (A–Z)
 * intended to be used to encode ISO 3166-1 alpha-2 two-letter country codes in a way
 * that allows optional special treatment.
 *
 * A pair of regional indicator symbols is referred to as an emoji flag sequence
 * (although it represents a specific region, not a specific flag for that region).
 *
 * 0x1F1E6 - Regional Indicator Symbol Letter A
 */
@SuppressWarnings("MagicNumber")
internal fun Country.emojiFlag(): String {
    if (this.iso3166Alpha2.length != 2) return ""

    val firstCapitalizedChar = 'A'
    val firstRegionalSymbol = 0x1F1E6
    val iso2 = this.iso3166Alpha2.uppercase()
    val firstLetter = iso2[0] - firstCapitalizedChar + firstRegionalSymbol
    val secondLetter = iso2[1] - firstCapitalizedChar + firstRegionalSymbol

    return String(Character.toChars(firstLetter)) + String(Character.toChars(secondLetter))
}

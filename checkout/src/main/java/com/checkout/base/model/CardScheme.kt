package com.checkout.base.model

import androidx.annotation.DrawableRes
import com.checkout.R

/**
 * Enum representing card scheme.
 *
 * Supported schemes:
 *   - American Express
 *   - Diner's Club
 *   - Discover
 *   - JCB
 *   - Mada
 *   - Maestro
 *   - Mastercard
 *   - Visa
 */
@Suppress("MagicNumber")
public enum class CardScheme(
    /**
     * Supported CVV length.
     */
    public val cvvLength: Set<Int>,
    /**
     * Regex for card scheme determination according to a full card number.
     */
    internal val regex: Regex,
    /**
     * Regex for card scheme determination according to a partial card number.
     */
    internal val eagerRegex: Regex,
    /**
     * Supported maximum card number length.
     */
    public val maxNumberLength: Int = 16,
    /**
     * Card number separator pattern.
     * Contains indexes for separator in transformed card number.
     */
    public val numberSeparatorPattern: List<Int> = listOf(4, 9, 14),
    /**
     * Card scheme icon.
     */
    @DrawableRes
    public val imageId: Int = R.drawable.cko_ic_scheme_placeholder
) {
    AMERICAN_EXPRESS(
        cvvLength = setOf(4),
        regex = Regex("^3[47]\\d{13}$"),
        eagerRegex = Regex("^3[47]\\d*$"),
        maxNumberLength = 15,
        numberSeparatorPattern = listOf(4, 11),
        imageId = R.drawable.cko_ic_scheme_amex
    ),
    DINERS_CLUB(
        cvvLength = setOf(3),
        regex = Regex("^3(0[0-5]|[68]\\d)\\d{11,16}$"),
        eagerRegex = Regex("^3(0|[68])\\d*$"),
        maxNumberLength = 19,
        numberSeparatorPattern = listOf(4, 9, 14, 19),
        imageId = R.drawable.cko_ic_scheme_diners
    ),
    DISCOVER(
        cvvLength = setOf(3),
        regex = Regex("^6(?:011(0[0-9]|[2-4]\\d|74|7[7-9]|8[6-9]|9[0-9])|4[4-9]\\d{3}|5\\d{4})\\d{10}$"),
        eagerRegex = Regex("^6(011(0[0-9]|[1-4]|74|7[7-9]|8[6-9]|9[0-9])|4[4-9]|5)\\d*$"),
        maxNumberLength = 19,
        numberSeparatorPattern = listOf(4, 9, 14, 19),
        imageId = R.drawable.cko_ic_scheme_discover
    ),
    JCB(
        cvvLength = setOf(3),
        regex = Regex("^35\\d{14}$"),
        eagerRegex = Regex("^35\\d*$"),
        maxNumberLength = 19,
        numberSeparatorPattern = listOf(4, 9, 14, 19),
        imageId = R.drawable.cko_ic_scheme_jcb
    ),
    MADA(
        cvvLength = setOf(3),
        regex = Regex(
            "^(4(0(0861|1757|6996|7(197|395)|9201)|1(2565|0685|7633|9593)|2(281(7|8|9)|8(331|67(1|2|3)))" +
                    "|3(1361|2328|4107|9954)|4(0(533|647|795)|5564|6(393|404|672))|5(5(036|708)|7865|7997|8456)" +
                    "|6(2220|854(0|1|2|3))|8(301(0|1|2)|4783|609(4|5|6)|931(7|8|9))|93428)|5(0(4300|6968|8160)" +
                    "|13213|2(0058|1076|4(130|514)|9(415|741))|3(0(060|906)|1(095|196)|2013|5(825|989)|6023|7767" +
                    "|9931)|4(3(085|357)|9760)|5(4180|7606|8848)|8(5265|8(8(4(5|6|7|8|9)|5(0|1))|98(2|3))" +
                    "|9(005|206)))|6(0(4906|5141)|36120)|9682(0(1|2|3|4|5|6|7|8|9)|1(0|1)))\\d{10}$"
        ),
        eagerRegex = Regex(
            "^(4(0(0861|1757|6996|7(197|395)|9201)|1(2565|0685|7633|9593)|2(281(7|8|9)|8(331|67(1|2|3)))" +
                    "|3(1361|2328|4107|9954)|4(0(533|647|795)|5564|6(393|404|672))|5(5(036|708)|7865|7997|8456)" +
                    "|6(2220|854(0|1|2|3))|8(301(0|1|2)|4783|609(4|5|6)|931(7|8|9))|93428)" +
                    "|5(0(4300|6968|8160)|13213|2(0058|1076|4(130|514)|9(415|741))" +
                    "|3(0(060|906)|1(095|196)|2013|5(825|989)|6023|7767|9931)|4(3(085|357)|9760)|5(4180|7606|8848)" +
                    "|8(5265|8(8(4(5|6|7|8|9)|5(0|1))|98(2|3))|9(005|206)))|6(0(4906|5141)|36120)" +
                    "|9682(0(1|2|3|4|5|6|7|8|9)|1(0|1)))\\d*$"
        ),
        imageId = R.drawable.cko_ic_scheme_mada
    ),
    MAESTRO(
        cvvLength = setOf(0, 3),
        regex = Regex(
            "^(?:5[06789]\\d\\d|(?!6011[0234])(?!60117[4789])(?!60118[6789])(?!60119)" +
                    "(?!64[456789])(?!65)6\\d{3})\\d{8,15}$"
        ),
        eagerRegex = Regex("^(5[0|6-8]\\d{0,17}|6\\d{0,18})$"),
        maxNumberLength = 19,
        numberSeparatorPattern = listOf(4, 9, 14, 19),
        imageId = R.drawable.cko_ic_scheme_maestro
    ),
    MASTERCARD(
        cvvLength = setOf(3),
        regex = Regex("^(5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)\\d{12}$"),
        eagerRegex = Regex("^(2[3-7]|22[2-9]|5[1-5])\\d*$"),
        imageId = R.drawable.cko_ic_scheme_mastercard
    ),
    VISA(
        cvvLength = setOf(3),
        regex = Regex("^4\\d{12}(\\d{3}|\\d{6})?$"),
        eagerRegex = Regex("^4\\d*$"),
        imageId = R.drawable.cko_ic_scheme_visa
    ),
    UNKNOWN(
        cvvLength = setOf(0, 3, 4),
        regex = Regex(""),
        eagerRegex = Regex(""),
        maxNumberLength = 19,
        numberSeparatorPattern = listOf(4, 9, 14, 19)
    );

    public companion object {
        public fun fetchAllSupportedCardSchemes(): List<CardScheme> = mutableListOf<CardScheme>().apply {
            addAll(values())
            remove(UNKNOWN)
        }
    }
}

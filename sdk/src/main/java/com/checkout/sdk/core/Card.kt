package com.checkout.sdk.core

import com.checkout.sdk.R


/**
 * An enum that hold information about the different card types.
 * The sported card types are: VISA, AMEX, DISCOVER, UNIONPAY, JCB,
 * LASER, DINERSCLUB, MASTERCARD, MAESTRO and a DEFAULT abstract card.
 */
enum class Card
/**
 * The [Card] constructor
 *
 *
 *
 * @param cardName      card name
 * @param pattern       pattern used to determine card type early
 * @param regex         full regex of a full card
 * @param maxCardLength the max length a card of a type can have
 * @param maxCvvLength  the max CVV a card of a type can have
 * @param gaps          the positions of the spaces spans ina formatted card
 * @see Card
 */
constructor(
    val cardName: String,
    val resourceId: Int,
    val pattern: String,
    val regex: String,
    val cardLength: IntArray,
    val maxCardLength: Int,
    val maxCvvLength: Int,
    val gaps: IntArray,
    val luhn: Boolean
) {
    VISA(
        "visa",
        R.drawable.visa,
        "^4\\d*$",
        "^4[0-9]{12}(?:[0-9]{3})?$",
        intArrayOf(13, 16),
        19,
        3,
        intArrayOf(4, 9, 14),
        true
    ),
    AMEX(
        "amex",
        R.drawable.amex,
        "^3[47]\\d*$",
        "/(\\d{1,4})(\\d{1,6})?(\\d{1,5})?/",
        intArrayOf(15),
        18,
        4,
        intArrayOf(4, 6),
        true
    ),
    DISCOVER(
        "discover",
        R.drawable.discover,
        "^(6011|65|64[4-9])\\d*$",
        "^6(?:011|5[0-9]{2})[0-9]{12}$",
        intArrayOf(16),
        23,
        3,
        intArrayOf(4, 9, 14),
        true
    ),
    UNIONPAY(
        "unionpay",
        R.drawable.unionpay,
        "^(((620|(621(?!83|88|98|99))|622(?!06|018)|62[3-6]|627[02,06,07]|628(?!0|1)|629[1,2]))\\d*|622018\\d{12})$",
        "^6(?:011|5[0-9]{2})[0-9]{12}$",
        intArrayOf(16, 17, 18, 19),
        23,
        3,
        intArrayOf(4, 6, 14),
        false
    ),
    JCB(
        "jcb",
        R.drawable.jcb,
        "^(2131|1800|35)\\d*$",
        "^(?:2131|1800|35[0-9]{3})[0-9]{11}$",
        intArrayOf(16),
        23,
        3,
        intArrayOf(4, 9, 14),
        true
    ),
    DINERSCLUB(
        "dinersclub",
        R.drawable.dinersclub,
        "^3(0[0-5]|[689])\\d*$",
        "^3(?:0[0-5]|[68][0-9])?[0-9]{11}$",
        intArrayOf(14),
        23,
        3,
        intArrayOf(4, 6),
        true
    ),
    MASTERCARD(
        "mastercard",
        R.drawable.mastercard,
        "^(5[1-5]|222[1-9]|22[3-9]|2[3-6]|27[0-1]|2720)\\d*$",
        "^5[1-5][0-9]{14}$",
        intArrayOf(16, 17),
        19,
        3,
        intArrayOf(4, 9, 14),
        true
    ),
    MAESTRO(
        "maestro",
        R.drawable.maestro,
        "^(?:5[06789]\\d\\d|(?!6011[0234])(?!60117[4789])(?!60118[6789])(?!60119)(?!64[456789])(?!65)6\\d{3})\\d{8,15}$",
        "^(5[06-9]|6[37])[0-9]{10,17}$",
        intArrayOf(12, 13, 14, 15, 16, 17, 18, 19),
        23,
        3,
        intArrayOf(4, 9, 14),
        true
    ),
    DEFAULT("default",
        0,
        "", "",
        intArrayOf(16),
        19,
        3,
        intArrayOf(4, 9, 14),
        false)
}

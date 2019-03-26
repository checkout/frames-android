package com.checkout.sdk.utils

import android.text.TextUtils

import com.checkout.sdk.core.Card

/**
 * Provide information about different card types.
 */
object CardUtils {

    /**
     * Returns a Cards object can be used to identify the card type and
     * information about: regex, card/cvv maximum length, space separation
     * The number argument must specify as a String.
     *
     *
     * This method iterates a Cards enum, and determines if the the function
     * argument matches any pattern. Based on the verification, a Cards object
     * will be returned.
     *
     * @param number the String value of a card number
     * @return Cards object for the given type found
     * @see Card
     */
    fun getType(number: String?): Card {
        var number = number

        // Remove spaces from The number String
        number = sanitizeEntry(number)
        val cards = Card.values()

        // Iterate over the card card types and check what pattern matches
        if (number != "") {
            for (card in cards) {
                if (number.matches(card.pattern.toRegex())) {
                    return card
                }
            }
        }

        // Return a default card if no card type is matched
        return Card.DEFAULT
    }

    /**
     * Returns a boolean showing is the card String is a valid card number.
     *
     *
     * This method is using the regex in [Card] as well as the Luhn algorithm to
     * the terms the validity of a card number
     *
     * @param number the String value of a card number
     * @return If the card number is valid or not
     */
    fun isValidCard(number: String?): Boolean {
        var number = number
        if (number == null || number == "") {
            return false
        }

        number = sanitizeEntry(number)
        val type = getType(number)
        // If the card is not on the available card list return false
        if (type === Card.DEFAULT) {
            return false
        }

        // Check if the length of the card matches the valid card lengths for the specific type
        var isValidLength = false
        for (length in type.cardLength) {
            if (number.length == length) {
                isValidLength = true
            }
        }

        // If the card length is valid and luhn is available check luhn, otherwise consider card valid
        if (isValidLength && type.luhn) {
            return checkLuhn(number)
        } else if (isValidLength && !type.luhn) {
            return true
        }

        return false
    }

    /**
     * Returns a boolean showing is the card String is a valid card number.
     *
     *
     * This is using Luhn validation to determine the card validity
     *
     * @param number the String value of a card number
     * @return If the card number passes Luhn validation
     */
    private fun checkLuhn(number: String): Boolean {
        val rev = StringBuffer(number).reverse().toString()
        val len = rev.length
        var oddSum = 0
        var evenSum = 0
        for (i in 0 until len) {
            val c = rev[i]
            val digit = Character.digit(c, 10)
            if (i % 2 == 0) {
                oddSum += digit
            } else {
                evenSum += digit / 5 + 2 * digit % 10
            }
        }
        return (oddSum + evenSum) % 10 == 0
    }

    /**
     * f
     * Returns a String without any spaces
     *
     *
     * This method used to take a card number input String and return a
     * String that simply removed all whitespace, keeping only digits.
     *
     * @param entry the String value of a card number
     * @return Cards object for the given type found
     */
    private fun sanitizeEntry(entry: String?): String {
        return entry!!.replace("\\D".toRegex(), "")
    }

    /**
     * The card formatting method
     *
     *
     * Used to take a card number String and provide formatting (span space characters)
     *
     * @param number card number in string format
     * @return processedCard
     */
    fun getFormattedCardNumber(number: String): String {

        // Remove spaces form the card String
        var processedCard = sanitizeEntry(number)

        val cardType = getType(number)

        // If the card is an AMEX or DINERSCLUB we iterate and span spaces at specific positions
        if (cardType.cardName.equals("amex") || cardType.cardName.equals("dinersclub") || cardType.cardName.equals(
                "unionpay"
            )
        ) {
            for (i in cardType.gaps.indices) {
                processedCard = processedCard.replaceFirst(
                    ("(\\d{" + cardType.gaps[i] + "})(?=\\d)").toRegex(),
                    "$1 "
                )
            }
            // If the card is on any other kind we span a space after every group of 4 digits
        } else {
            processedCard = processedCard.replace("(\\d{4})(?=\\d)".toRegex(), "$1 ")
        }

        return processedCard
    }

    /**
     * Returns a boolean showing is the cvv is valid in relation to the card type
     *
     *
     * Used to take a card number String and provide formatting (span space characters)
     *
     * @param cvv  the card cvv
     * @param card the card object
     * @return boolean representing validity
     */
    fun isValidCvv(cvv: String, card: Card?): Boolean {
        if (TextUtils.isDigitsOnly(sanitizeEntry(cvv)) && card != null) {
            if (card.maxCvvLength == cvv.length)
                return true
        }
        return false
    }
}

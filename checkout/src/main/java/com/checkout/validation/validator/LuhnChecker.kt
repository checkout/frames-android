package com.checkout.validation.validator

import com.checkout.validation.validator.contract.Checker

/**
 * Class used to validate card number with Luhn algorithm.
 */
internal class LuhnChecker : Checker<String> {

    /**
     * Checks the given [String].
     *
     * @param data - The card number provided for validation of type [String].
     * @return [Boolean] true if card number check passed, otherwise false.
     */
    override fun check(data: String): Boolean = checkLuhn(data)

    /**
     * Returns a boolean showing is the card [String] is a valid card number.
     * This is using Luhn validation to determine the card validity.
     *
     * @param number - The [String] value of a card number.
     * @return true if the card number passes Luhn validation.
     */
    @Suppress("MagicNumber")
    private fun checkLuhn(number: String): Boolean {
        val reversedNumber = number.reversed()
        var oddSum = 0
        var evenSum = 0

        for (i in reversedNumber.indices) {
            val digit = reversedNumber[i].digitToIntOrNull() ?: return false

            if (i % 2 == 0) {
                oddSum += digit
            } else {
                evenSum += digit / 5 + 2 * digit % 10
            }
        }

        return (oddSum + evenSum) % 10 == 0
    }
}

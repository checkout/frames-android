package com.checkout.frames.component.expirydate

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.checkout.frames.utils.constants.EXPIRY_DATE_ZERO_POSITION_CHECK
import com.checkout.frames.utils.constants.EXPIRY_DATE_PREFIX_ZERO

internal class ExpiryDateVisualTransformation : VisualTransformation {

    private val separator = " / "

    override fun filter(text: AnnotatedString) = performExpiryDateFilter(text)

    private fun performExpiryDateFilter(text: AnnotatedString): TransformedText {
        /* format: MM/YY
         * Depending on the first number is where the separator will be placed
         * If the first number is 2-9 then the slash will come after the
         * 2, if the first number is 11 or 12 it will be after the second digit,
         * if the number is 01 it will be after the second digit.
         */
        val isSingleDigitMonth = text.isNotBlank() && !(text[0] == '0' || text[0] == '1')

        val output = buildString {
            for ((index, char) in text.withIndex()) {
                if (isSingleDigitMonth && index == 0 && char > EXPIRY_DATE_ZERO_POSITION_CHECK) {
                    append(EXPIRY_DATE_PREFIX_ZERO + char + separator)
                } else {
                    append(char)
                }

                if (index == 1 && !this.contains(separator)) {
                    append(separator)
                }
            }
        }

        val outputOffsets = calculateOutputOffsets(output)
        val separatorIndices = calculateSeparatorOffsets(output)

        val offsetTranslator = object : OffsetMapping {

            override fun originalToTransformed(offset: Int): Int {
                val updatedOffset: Int = if (isSingleDigitMonth) offset + 1 else offset

                return outputOffsets[updatedOffset]
            }

            override fun transformedToOriginal(offset: Int): Int {
                val updatedOffset: Int = if (isSingleDigitMonth && offset > 0) offset - 1 else offset
                val separatorCharactersBeforeOffset = separatorIndices.count { it < offset }

                return updatedOffset - separatorCharactersBeforeOffset
            }
        }

        return TransformedText(AnnotatedString(output), offsetTranslator)
    }

    private fun calculateOutputOffsets(output: String): List<Int> {
        val digitOffsets = output.mapIndexedNotNull { index, char ->
            // +1 because we're looking for offsets, not indices
            index.takeIf { char.isDigit() }?.plus(1)
        }
        // We're adding 0 so that the cursor can be placed at the start of the text,
        // and replace the last digit offset with the length of the output. The latter
        // is so that the offsets are set correctly for text such as "04 / ".
        return listOf(0) + digitOffsets.dropLast(1) + output.length
    }

    private fun calculateSeparatorOffsets(output: String): List<Int> {
        return output.mapIndexedNotNull { index, c ->
            index.takeUnless { c.isDigit() }
        }
    }
}

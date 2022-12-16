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
        // format: XX/XX

        val stringBuilder = StringBuilder()
        for (i in text.indices) {
            when {
                i == 0 && text[i] > EXPIRY_DATE_ZERO_POSITION_CHECK -> {
                    stringBuilder.append(EXPIRY_DATE_PREFIX_ZERO + text[i] + separator)
                }
                i == 1 && !stringBuilder.contains(separator) -> {
                    stringBuilder.append(text[i] + separator)
                }
                else -> {
                    stringBuilder.append(text[i])
                }
            }
        }

        val expiryDateOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                val withZero = text.isNotEmpty() && text[0] > EXPIRY_DATE_ZERO_POSITION_CHECK
                val zeroPrefix = if (withZero) 1 else 0

                return when {
                    offset == 0 -> offset
                    !withZero && offset < 2 -> offset
                    else -> offset + separator.length + zeroPrefix
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                val withZero = text.isNotEmpty() && text[0] > EXPIRY_DATE_ZERO_POSITION_CHECK
                val zeroPrefix = if (withZero) 1 else 0

                return when {
                    offset == 0 -> offset
                    !withZero && offset < 2 -> offset
                    else -> offset - separator.length - zeroPrefix
                }
            }
        }

        return TransformedText(AnnotatedString(stringBuilder.toString()), expiryDateOffsetTranslator)
    }
}

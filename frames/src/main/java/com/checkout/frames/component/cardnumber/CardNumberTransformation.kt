package com.checkout.frames.component.cardnumber

import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.checkout.base.model.CardScheme
import java.lang.StringBuilder

internal class CardNumberTransformation(
    private val separator: Char,
    private val cardScheme: MutableState<CardScheme>
) : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText = defaultCardNumberFormat(text)

    private fun defaultCardNumberFormat(text: AnnotatedString): TransformedText {
        val pattern: List<Int> = cardScheme.value.numberSeparatorPattern
        val strBuilder = StringBuilder()

        for (i in text.indices) {
            strBuilder.append(text[i])
            if (strBuilder.length in pattern) strBuilder.append(separator)
        }

        val creditCardOffsetTranslator = object : OffsetMapping {

            override fun originalToTransformed(offset: Int): Int {
                pattern.forEachIndexed { index, separatorPosition ->
                    if (offset < separatorPosition - index) return offset + index
                }

                return offset + pattern.size
            }

            override fun transformedToOriginal(offset: Int): Int {
                pattern.forEachIndexed { index, separatorPosition ->
                    if (offset <= separatorPosition) return offset - index
                }

                return offset - pattern.size
            }
        }

        return TransformedText(AnnotatedString(strBuilder.toString()), creditCardOffsetTranslator)
    }
}

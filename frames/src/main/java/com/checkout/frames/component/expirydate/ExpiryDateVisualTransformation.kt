package com.checkout.frames.component.expirydate

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

internal class ExpiryDateVisualTransformation : VisualTransformation {

    private val separator = " / "

    override fun filter(text: AnnotatedString) = performExpiryDateFilter(text)

    private fun performExpiryDateFilter(text: AnnotatedString): TransformedText {
        // format: XX/XX
        val strBuilder = StringBuilder()
        for (i in text.text.indices) {
            strBuilder.append(text.text[i])
            if (i == 1) strBuilder.append(separator)
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int) =
                if (offset <= 1) offset else offset + separator.length

            override fun transformedToOriginal(offset: Int) =
                if (offset <= 2) offset else offset - separator.length
        }

        return TransformedText(AnnotatedString(strBuilder.toString()), offsetMapping)
    }
}

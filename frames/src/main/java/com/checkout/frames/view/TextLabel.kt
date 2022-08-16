package com.checkout.frames.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.checkout.frames.mapper.ImageStyleToComposableImageMapper
import com.checkout.frames.model.Padding
import com.checkout.frames.style.component.base.ImageStyle
import com.checkout.frames.style.view.TextLabelViewStyle

@Composable
internal fun TextLabel(
    style: TextLabelViewStyle,
    state: TextLabelState
) = Row(
    modifier = style.modifier,
    verticalAlignment = Alignment.CenterVertically
) {
    val text = state.textId.value?.let { stringResource(id = it) } ?: state.text.value
    with(style) {
        state.leadingIcon.value?.let { it() }
        Text(
            text,
            Modifier,
            color,
            fontSize,
            fontStyle,
            fontWeight,
            fontFamily,
            letterSpacing,
            textDecoration,
            textAlign,
            lineHeight,
            overflow,
            softWrap,
            maxLines,
            onTextLayout,
            this.style ?: LocalTextStyle.current
        )
        state.trailingIcon.value?.let { it() }
    }
}

/* ------------------------------ Preview ------------------------------ */

@SuppressLint("UnrememberedMutableState", "MagicNumber")
@Preview(showBackground = true, name = "Default InputField")
@Composable
private fun TextLabelPreview() {
    val imageMapper = ImageStyleToComposableImageMapper()
    val imageStyle = ImageStyle(
        image = android.R.drawable.ic_dialog_map,
        tinColor = 0xFFFF00FF,
        height = 48,
        width = 48,
        Padding(start = 8, end = 8)
    )

    TextLabel(
        style = TextLabelViewStyle(
            style = TextStyle(
                color = Color.Gray
            )
        ),
        state = TextLabelState(
            mutableStateOf("Test label text"),
            leadingIcon = mutableStateOf(imageMapper.map(imageStyle)),
            trailingIcon = mutableStateOf(imageMapper.map(imageStyle))
        )
    )
}

package com.checkout.frames.mapper

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.checkout.base.mapper.Mapper
import com.checkout.frames.style.component.base.ImageStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.view.TextLabelState

internal class TextLabelStyleToStateMapper(
    private val imageMapper: Mapper<ImageStyle?, @Composable (() -> Unit)?>
) : Mapper<TextLabelStyle?, TextLabelState> {

    override fun map(from: TextLabelStyle?): TextLabelState = TextLabelState(
        mutableStateOf(from?.text ?: ""),
        leadingIcon = mutableStateOf(imageMapper.map(from?.leadingIconStyle)),
        trailingIcon = mutableStateOf(imageMapper.map(from?.trailingIconStyle)),
        isVisible = mutableStateOf(from?.text?.isNotBlank() == true)
    )
}

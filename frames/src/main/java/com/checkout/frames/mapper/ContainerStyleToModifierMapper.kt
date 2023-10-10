package com.checkout.frames.mapper

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.checkout.base.mapper.Mapper
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.utils.extensions.toComposeShape
import com.checkout.frames.utils.extensions.toComposeStroke

internal class ContainerStyleToModifierMapper : Mapper<ContainerStyle, Modifier> {

    @SuppressLint("ModifierFactoryExtensionFunction")
    override fun map(from: ContainerStyle): Modifier = with(from) {
        val composeShape = shape.toComposeShape(cornerRadius)
        var modifier = Modifier.background(Color.Transparent)

        height?.let { modifier = modifier.height(it.dp) }
        width?.let { modifier = modifier.width(it.dp) }
        margin?.let { modifier = modifier.padding(it.start.dp, it.top.dp, it.end.dp, it.bottom.dp) }

        modifier = modifier.background(
            color = Color(color),
            shape = composeShape,
        )

        borderStroke?.let { modifier = modifier.border(it.toComposeStroke(), composeShape) }

        padding?.let { modifier = modifier.padding(it.start.dp, it.top.dp, it.end.dp, it.bottom.dp) }

        return modifier
    }
}

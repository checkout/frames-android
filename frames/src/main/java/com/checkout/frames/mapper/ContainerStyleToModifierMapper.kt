package com.checkout.frames.mapper

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.checkout.base.mapper.Mapper
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.utils.extensions.toComposeShape

internal class ContainerStyleToModifierMapper : Mapper<ContainerStyle, Modifier> {

    @SuppressLint("ModifierFactoryExtensionFunction")
    override fun map(from: ContainerStyle): Modifier = with(from) {
        var modifier = Modifier.background(Color.Transparent)

        height?.let { modifier = modifier.height(it.dp) }
        width?.let { modifier = modifier.width(it.dp) }
        margin?.let { modifier = modifier.padding(it.start.dp, it.top.dp, it.end.dp, it.bottom.dp) }

        modifier = modifier.background(
            color = Color(color),
            shape = shape.toComposeShape(cornerRadius)
        )

        padding?.let { modifier = modifier.padding(it.start.dp, it.top.dp, it.end.dp, it.bottom.dp) }

        return modifier
    }
}

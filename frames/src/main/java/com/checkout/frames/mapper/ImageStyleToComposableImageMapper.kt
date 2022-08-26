package com.checkout.frames.mapper

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.checkout.base.mapper.Mapper
import com.checkout.frames.style.component.base.ImageStyle

internal class ImageStyleToComposableImageMapper : Mapper<ImageStyle?, @Composable (() -> Unit)?> {

    override fun map(from: ImageStyle?): @Composable (() -> Unit)? = from?.let { @Composable { LabelImage(it) } }

    @Composable
    private fun LabelImage(style: ImageStyle) = style.image?.let { imageId ->
        val image = painterResource(imageId)
        var modifier = Modifier.wrapContentHeight().wrapContentWidth()

        style.padding?.let { modifier = modifier.padding(it.start.dp, it.top.dp, it.end.dp, it.bottom.dp) }
        style.height?.let { modifier = modifier.height(it.dp) }
        style.width?.let { modifier = modifier.width(it.dp) }

        Image(
            modifier = modifier,
            painter = image,
            contentDescription = "Label image",
            colorFilter = style.tinColor?.let { ColorFilter.tint(Color(it)) }
        )
    }
}

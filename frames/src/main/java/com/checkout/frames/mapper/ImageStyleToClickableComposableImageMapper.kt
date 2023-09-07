package com.checkout.frames.mapper

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.checkout.base.mapper.Mapper
import com.checkout.frames.R
import com.checkout.frames.model.request.ImageStyleToClickableImageRequest

internal class ImageStyleToClickableComposableImageMapper :
    Mapper<ImageStyleToClickableImageRequest?, @Composable (() -> Unit)?> {

    override fun map(from: ImageStyleToClickableImageRequest?): @Composable (() -> Unit)? =
        from?.let { @Composable { LabelImage(it) } }

    @Composable
    private fun LabelImage(request: ImageStyleToClickableImageRequest) = request.style?.image?.let { imageId ->
        val composableImage: @Composable (() -> Unit)
        val image = painterResource(imageId)
        var modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth()

        with(request.style) {
            padding?.let { modifier = modifier.padding(it.start.dp, it.top.dp, it.end.dp, it.bottom.dp) }
            height?.let { modifier = modifier.height(it.dp) }
            width?.let { modifier = modifier.width(it.dp) }
        }

        composableImage = @Composable {
            Image(
                modifier = modifier,
                painter = image,
                contentDescription = stringResource(R.string.cko_content_description_image),
                colorFilter = request.style.tinColor?.let { ColorFilter.tint(Color(it)) }
            )
        }

        IconButton(onClick = { request.onImageClick() }) { composableImage() }
    }
}

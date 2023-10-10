package com.checkout.frames.mapper

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.checkout.base.mapper.Mapper
import com.checkout.frames.R
import com.checkout.frames.model.request.ImageStyleToDynamicImageRequest

internal class ImageStyleToDynamicComposableImageMapper :
    Mapper<ImageStyleToDynamicImageRequest, @Composable (() -> Unit)> {

    override fun map(from: ImageStyleToDynamicImageRequest): @Composable (() -> Unit) = @Composable { Image(from) }

    @Composable
    private fun Image(request: ImageStyleToDynamicImageRequest) = with(request) {
        dynamicImageId.collectAsState(initial = null).value?.let { imageId ->
            val composableImage: @Composable (() -> Unit)
            val image = painterResource(id = imageId)
            var modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()

            style?.padding?.let { modifier = modifier.padding(it.start.dp, it.top.dp, it.end.dp, it.bottom.dp) }
            style?.height?.let { modifier = modifier.height(it.dp) }
            style?.width?.let { modifier = modifier.width(it.dp) }

            composableImage = @Composable {
                Image(
                    modifier = modifier,
                    painter = image,
                    contentDescription = stringResource(R.string.cko_content_description_dynamic_image),
                    colorFilter = style?.tinColor?.let { ColorFilter.tint(Color(it)) },
                )
            }

            onImageClick.collectAsState(initial = null).value?.let {
                IconButton(onClick = it) { composableImage() }
            } ?: composableImage()
        }
    }
}

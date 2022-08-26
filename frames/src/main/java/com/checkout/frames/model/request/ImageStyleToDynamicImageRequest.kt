package com.checkout.frames.model.request

import com.checkout.frames.style.component.base.ImageStyle
import kotlinx.coroutines.flow.Flow

internal data class ImageStyleToDynamicImageRequest(
    val style: ImageStyle?,
    val dynamicImageId: Flow<Int?>
)

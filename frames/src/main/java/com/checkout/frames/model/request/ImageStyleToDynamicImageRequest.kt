package com.checkout.frames.model.request

import com.checkout.frames.style.component.base.ImageStyle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

internal data class ImageStyleToDynamicImageRequest(
    val style: ImageStyle?,
    val dynamicImageId: Flow<Int?>,
    val onImageClick: Flow<(() -> Unit)?> = flowOf(null),
)

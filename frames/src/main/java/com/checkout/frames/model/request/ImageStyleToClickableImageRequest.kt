package com.checkout.frames.model.request

import com.checkout.frames.style.component.base.ImageStyle

internal data class ImageStyleToClickableImageRequest(
    val style: ImageStyle?,
    val onImageClick: () -> Unit = {},
)

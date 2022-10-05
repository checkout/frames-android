package com.checkout.frames.style.component

import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.ImageStyle
import com.checkout.frames.style.component.base.TextLabelStyle

public data class CardSchemeComponentStyle(
    val titleStyle: TextLabelStyle = TextLabelStyle(),
    val imageStyle: ImageStyle? = null,
    val imagesContainerStyle: ContainerStyle = ContainerStyle()
)

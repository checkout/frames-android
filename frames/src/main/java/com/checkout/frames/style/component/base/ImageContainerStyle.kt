package com.checkout.frames.style.component.base

public data class ImageContainerStyle @JvmOverloads constructor(
    /** The main axis spacing between the children of each row. */
    val mainAxisSpacing: Int = 0,
    /**  The cross axis spacing between the rows of the layout. */
    val crossAxisSpacing: Int = 0,
    /** supported images container style. */
    val containerStyle: ContainerStyle = ContainerStyle()
)

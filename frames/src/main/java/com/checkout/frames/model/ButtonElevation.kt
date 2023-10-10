package com.checkout.frames.model

public data class ButtonElevation(
    val defaultElevation: Int = 0,
    val pressedElevation: Int = 0,
    val focusedElevation: Int = 0,
    val hoveredElevation: Int = 0,
    val disabledElevation: Int = 0,
) {
    public constructor(elevation: Int) : this(elevation, elevation, elevation, elevation, elevation)
}

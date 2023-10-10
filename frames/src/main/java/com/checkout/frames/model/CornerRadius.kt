package com.checkout.frames.model

public data class CornerRadius(
    val topStart: Int = 0,
    val topEnd: Int = 0,
    val bottomEnd: Int = 0,
    val bottomStart: Int = 0,
) {
    public constructor(radius: Int) : this(radius, radius, radius, radius)
}

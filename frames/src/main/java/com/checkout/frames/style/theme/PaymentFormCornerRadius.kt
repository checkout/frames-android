package com.checkout.frames.style.theme

public data class PaymentFormCornerRadius(
    val topStart: Int = 0,
    val topEnd: Int = 0,
    val bottomEnd: Int = 0,
    val bottomStart: Int = 0
) {
    public constructor(radius: Int) : this(radius, radius, radius, radius)
}

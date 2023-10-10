package com.checkout.frames.model

public data class Margin(
    /** Top margin in dp. */
    val top: Int = 0,
    /** Bottom margin in dp. */
    val bottom: Int = 0,
    /** Start margin in dp. */
    val start: Int = 0,
    /** End margin in dp. */
    val end: Int = 0,
) {
    public constructor(margin: Int) : this(margin, margin, margin, margin)
}

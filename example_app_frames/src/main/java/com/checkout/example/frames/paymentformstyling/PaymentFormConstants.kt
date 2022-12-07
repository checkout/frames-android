package com.checkout.example.frames.paymentformstyling

import com.checkout.frames.model.CornerRadius
import com.checkout.frames.model.Shape

object PaymentFormConstants {
    const val backgroundColor: Long = 0XFFFFCDC2
    const val inputFieldColor: Long = 0XFFFFFFFF
    const val textColor: Long = 0XFF461E67
    const val placeHolderTextColor: Long = 0XFFACA2B4
    /** Screen padding in dp. */
    const val padding: Int = 16
    const val paddingOneDp: Int = 1
    const val backIconSize: Int = 28

    /** Field bottom margin in dp. */
    const val marginBottom: Int = 24
    const val marginTop: Int = 16
    const val margin: Int = 10

    val cornerRadius = 12
    val inputFieldBorderShape = Shape.RoundCorner
    val inputFieldCornerRadius = CornerRadius(cornerRadius)
}

package com.checkout.frames.utils.extensions

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.checkout.frames.model.CornerRadius
import com.checkout.frames.model.Shape.Circle
import com.checkout.frames.model.Shape.CutCorner
import com.checkout.frames.model.Shape.RoundCorner

internal fun com.checkout.frames.model.Shape.toComposeShape(cornerRadius: CornerRadius): Shape = when (this) {
    Circle -> CircleShape
    RoundCorner -> RoundedCornerShape(
        cornerRadius.topStart.dp,
        cornerRadius.topEnd.dp,
        cornerRadius.bottomEnd.dp,
        cornerRadius.bottomStart.dp,
    )

    CutCorner -> CutCornerShape(
        cornerRadius.topStart.dp,
        cornerRadius.topEnd.dp,
        cornerRadius.bottomEnd.dp,
        cornerRadius.bottomStart.dp,
    )

    else -> RectangleShape
}

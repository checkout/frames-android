package com.checkout.frames.style.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal data class InternalButtonViewStyle(
    /** Elevation. */
    val defaultElevation: Dp = 0.dp,
    val pressedElevation: Dp = 0.dp,
    val focusedElevation: Dp = 0.dp,
    val hoveredElevation: Dp = 0.dp,
    val disabledElevation: Dp = 0.dp,

    /** Colors. */
    val containerColor: Color = Color.Transparent,
    val disabledContainerColor: Color = Color.Transparent,
    val contentColor: Color = Color.Black,
    val disabledContentColor: Color = Color.Gray,

    var modifier: Modifier = Modifier,
    val shape: Shape,
    val border: BorderStroke? = null,
    val contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    val textStyle: TextLabelViewStyle
)

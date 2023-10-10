package com.checkout.frames.style.view

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Style used to setup card compose FlowRow.
 *
 * @param mainAxisSpacing - The input text to be shown in the text field
 * @param crossAxisSpacing - The cross axis spacing between the rows of the layout
 * @param imagesContainerModifier - The [Modifier] to be applied to this images container
 * **/
internal data class FlowRowViewStyle(
    val mainAxisSpacing: Dp = 0.dp,
    val crossAxisSpacing: Dp = 0.dp,
    val imagesContainerModifier: Modifier = Modifier,
)

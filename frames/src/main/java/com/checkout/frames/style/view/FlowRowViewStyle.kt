package com.checkout.frames.style.view

import androidx.compose.ui.Modifier

/**
 * Style used to setup card compose FlowRow.
 *
 * @param mainAxisSpacing the input text to be shown in the text field
 * @param crossAxisSpacing The cross axis spacing between the rows of the layout
 * @param imagesContainerModifier the [Modifier] to be applied to this images container
 * **/
internal data class FlowRowViewStyle(
    val mainAxisSpacing: Int = 0,
    val crossAxisSpacing: Int = 0,
    val imagesContainerModifier: Modifier = Modifier
)

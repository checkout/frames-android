package com.checkout.frames.style.view

import androidx.compose.ui.Modifier

/**
 * Style used to setup card scheme component.
 *
 * @param mainAxisSpacing the input text to be shown in the text field
 * @param crossAxisSpacing The cross axis spacing between the rows of the layout
 * @param titleStyle the inputStyle to be applied to the label for the card scheme component.
 * @param imagesContainerModifier the [Modifier] to be applied to this images container
 * @param containerModifier the [Modifier] to be applied to this card scheme component
 * **/
internal data class CardSchemeComponentViewStyle(
    val mainAxisSpacing: Int = 0,
    val crossAxisSpacing: Int = 0,
    val titleStyle: TextLabelViewStyle,
    val imagesContainerModifier: Modifier = Modifier,
    val containerModifier: Modifier = Modifier
)

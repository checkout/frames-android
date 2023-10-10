package com.checkout.frames.style.view

import androidx.compose.ui.Modifier

/**
 * Style used to setup card scheme component.
 * @param titleStyle the inputStyle to be applied to the label for the card scheme component.
 * @param containerModifier the [Modifier] to be applied to this card scheme component
 * @param flowRowViewStyle style used to setup card compose FlowRow.
 * **/
internal data class CardSchemeComponentViewStyle(
    val titleStyle: TextLabelViewStyle,
    val containerModifier: Modifier = Modifier,
    val flowRowViewStyle: FlowRowViewStyle,
)

package com.checkout.frames.component.cardscheme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.checkout.frames.di.base.Injector
import com.checkout.frames.style.component.CardSchemeComponentStyle
import com.checkout.frames.style.view.CardSchemeComponentViewStyle
import com.checkout.frames.view.TextLabel
import com.google.accompanist.flowlayout.FlowRow

@Composable
internal fun CardSchemeComponent(
    style: CardSchemeComponentStyle,
    injector: Injector
) {
    val viewModel: CardSchemeViewModel = viewModel(
        factory = CardSchemeViewModel.Factory(injector, style)
    )

    BasicCardSchemeComponent(
        viewModel.componentStyle,
        viewModel.componentState,
        viewModel.componentSupportedCardSchemeIcons
    )
}

@Composable
private fun BasicCardSchemeComponent(
    style: CardSchemeComponentViewStyle,
    state: CardSchemeComponentState,
    supportedCardSchemeIconList: List<@Composable (() -> Unit)?>
) = with(state) {
    Column(modifier = style.containerModifier.wrapContentHeight()) {
        // Title label
        if (textLabelState?.isVisible?.value == true) TextLabel(style.titleStyle, textLabelState)

        FlowRow(style.containerModifier) {
            supportedCardSchemeIconList.forEach { it?.invoke() }
        }
    }
}

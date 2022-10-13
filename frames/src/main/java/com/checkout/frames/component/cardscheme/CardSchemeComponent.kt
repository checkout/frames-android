package com.checkout.frames.component.cardscheme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
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
) = with(style) {
    Column(modifier = style.containerModifier.wrapContentHeight()) {
        // Title label
        state.textLabelState?.let { state ->
            if (state.isVisible.value) TextLabel(titleStyle, state)
        }

        FlowRow(
            modifier = flowRowViewStyle.imagesContainerModifier,
            mainAxisSpacing = flowRowViewStyle.mainAxisSpacing.dp,
            crossAxisSpacing = flowRowViewStyle.crossAxisSpacing.dp
        ) {
            supportedCardSchemeIconList.forEach { it?.invoke() }
        }
    }
}

package com.checkout.frames.component.cardscheme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
internal fun BasicCardSchemeComponent(
    style: CardSchemeComponentViewStyle,
    state: CardSchemeComponentState,
    supportedCardSchemeIconList: List<@Composable (() -> Unit)?>
) = with(state) {
    Column(modifier = style.containerModifier.wrapContentHeight()) {
        // Title label
        if (textLabelState?.isVisible?.value == true) TextLabel(style.titleStyle, textLabelState)

        Spacer(modifier = Modifier.padding(top = 8.dp))

        FlowRow {
            supportedCardSchemeIconList.forEach { it?.invoke() }
        }
    }
}

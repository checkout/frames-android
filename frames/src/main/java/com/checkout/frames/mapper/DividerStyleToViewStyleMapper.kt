package com.checkout.frames.mapper

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.checkout.base.mapper.Mapper
import com.checkout.frames.model.Margin
import com.checkout.frames.style.component.base.DividerStyle
import com.checkout.frames.style.view.DividerViewStyle

internal class DividerStyleToViewStyleMapper : Mapper<DividerStyle, DividerViewStyle> {

    override fun map(from: DividerStyle): DividerViewStyle = with(from) {
        DividerViewStyle(
            thickness = thickness.dp,
            color = Color(color),
            modifier = Modifier
                .withMargin(margin)
                .fillMaxWidth(),
        )
    }

    private fun Modifier.withMargin(margin: Margin?): Modifier = this.apply {
        margin?.let { this.padding(it.start.dp, it.top.dp, it.end.dp, it.bottom.dp) }
    }
}

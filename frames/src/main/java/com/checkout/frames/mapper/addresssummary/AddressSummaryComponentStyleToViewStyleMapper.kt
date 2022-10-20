package com.checkout.frames.mapper.addresssummary

import androidx.compose.ui.Modifier
import com.checkout.base.mapper.Mapper
import com.checkout.frames.style.component.addresssummary.AddressSummaryComponentStyle
import com.checkout.frames.style.component.addresssummary.AddressSummarySectionStyle
import com.checkout.frames.style.component.base.ButtonStyle
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.style.view.InternalButtonViewStyle
import com.checkout.frames.style.view.TextLabelViewStyle
import com.checkout.frames.style.view.addresssummary.AddressSummaryComponentViewStyle
import com.checkout.frames.style.view.addresssummary.AddressSummarySectionViewStyle

internal class AddressSummaryComponentStyleToViewStyleMapper(
    private val textLabelMapper: Mapper<TextLabelStyle, TextLabelViewStyle>,
    private val buttonMapper: Mapper<ButtonStyle, InternalButtonViewStyle>,
    private val summarySectionMapper: Mapper<AddressSummarySectionStyle, AddressSummarySectionViewStyle>,
    private val containerMapper: Mapper<ContainerStyle, Modifier>
) : Mapper<AddressSummaryComponentStyle, AddressSummaryComponentViewStyle> {

    override fun map(from: AddressSummaryComponentStyle): AddressSummaryComponentViewStyle = with(from) {
        AddressSummaryComponentViewStyle(
            titleStyle = titleStyle?.let { textLabelMapper.map(it) },
            subTitleStyle = subTitleStyle?.let { textLabelMapper.map(it) },
            addAddressButtonStyle = buttonMapper.map(addAddressButtonStyle),
            summarySectionStyle = summarySectionMapper.map(summarySectionStyle),
            modifier = containerMapper.map(containerStyle)
        )
    }
}

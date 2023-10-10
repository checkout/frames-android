package com.checkout.frames.mapper.addresssummary

import androidx.compose.ui.Modifier
import com.checkout.base.mapper.Mapper
import com.checkout.frames.style.component.addresssummary.AddressSummarySectionStyle
import com.checkout.frames.style.component.base.ButtonStyle
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.DividerStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.style.view.DividerViewStyle
import com.checkout.frames.style.view.InternalButtonViewStyle
import com.checkout.frames.style.view.TextLabelViewStyle
import com.checkout.frames.style.view.addresssummary.AddressSummarySectionViewStyle

internal class AddressSummarySectionStyleToViewStyleMapper(
    private val textLabelMapper: Mapper<TextLabelStyle, TextLabelViewStyle>,
    private val dividerMapper: Mapper<DividerStyle, DividerViewStyle>,
    private val buttonMapper: Mapper<ButtonStyle, InternalButtonViewStyle>,
    private val containerMapper: Mapper<ContainerStyle, Modifier>,
) : Mapper<AddressSummarySectionStyle, AddressSummarySectionViewStyle> {

    override fun map(from: AddressSummarySectionStyle): AddressSummarySectionViewStyle = with(from) {
        AddressSummarySectionViewStyle(
            addressTextStyle = textLabelMapper.map(addressTextStyle),
            dividerStyle = dividerStyle?.let { dividerMapper.map(dividerStyle) },
            editAddressButtonStyle = buttonMapper.map(editAddressButtonStyle),
            modifier = containerMapper.map(containerStyle),
        )
    }
}

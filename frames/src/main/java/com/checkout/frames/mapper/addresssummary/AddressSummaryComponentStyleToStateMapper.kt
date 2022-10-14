package com.checkout.frames.mapper.addresssummary

import com.checkout.base.mapper.Mapper
import com.checkout.frames.component.addresssummary.AddressSummaryComponentState
import com.checkout.frames.style.component.addresssummary.AddressSummaryComponentStyle
import com.checkout.frames.style.component.base.ButtonStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.view.InternalButtonState
import com.checkout.frames.view.TextLabelState

internal class AddressSummaryComponentStyleToStateMapper(
    private val textLabelStateMapper: Mapper<TextLabelStyle?, TextLabelState>,
    private val buttonStateMapper: Mapper<ButtonStyle, InternalButtonState>
) : Mapper<AddressSummaryComponentStyle, AddressSummaryComponentState> {

    override fun map(from: AddressSummaryComponentStyle): AddressSummaryComponentState = with(from) {
        AddressSummaryComponentState(
            titleState = textLabelStateMapper.map(titleStyle),
            subTitleState = textLabelStateMapper.map(subTitleStyle),
            addressPreviewState = textLabelStateMapper.map(summarySectionStyle.addressTextStyle),
            editAddressButtonState = buttonStateMapper.map(summarySectionStyle.editAddressButtonStyle),
            addAddressButtonState = buttonStateMapper.map(addAddressButtonStyle),
        )
    }
}

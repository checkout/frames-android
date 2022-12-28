package com.checkout.frames.style.component.default

import com.checkout.frames.R
import com.checkout.frames.model.Margin
import com.checkout.frames.model.CornerRadius
import com.checkout.frames.model.Shape
import com.checkout.frames.model.BorderStroke
import com.checkout.frames.style.component.addresssummary.AddressSummaryComponentStyle
import com.checkout.frames.style.component.addresssummary.AddressSummarySectionStyle
import com.checkout.frames.style.component.base.DividerStyle
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.utils.constants.AddressSummaryConstants
import com.checkout.frames.utils.constants.BorderConstants
import com.checkout.frames.utils.constants.LightStyleConstants
import com.checkout.frames.utils.constants.PaymentDetailsScreenConstants

public object DefaultAddressSummaryComponentStyle {

    @JvmOverloads
    public fun light(isOptional: Boolean = false): AddressSummaryComponentStyle {
        val titleStyle = DefaultTextLabelStyle.title(textId = R.string.cko_billing_address)

        /**
         * For billing summary, if field is optional, required to hide subtitle from the screen
         */
        val subTitleStyle =
            if (isOptional) null
            else DefaultTextLabelStyle.subtitle(textId = R.string.cko_billing_address_info)

        val addAddressButtonStyle = DefaultButtonStyle.lightOutline(
            textId = R.string.cko_add_billing_address,
            trailingIconStyle = DefaultImageStyle.buttonTrailingImageStyle(),
            margin = Margin(
                top = AddressSummaryConstants.marginBeforeSummarySection,
                bottom = PaymentDetailsScreenConstants.marginBottom
            )
        )
        val summarySectionStyle = AddressSummarySectionStyle(
            addressTextStyle = DefaultTextLabelStyle.text(
                padding = AddressSummaryConstants.addressSummaryTextPadding,
                lineHeight = AddressSummaryConstants.addressSummaryTextLineHeight
            ),
            dividerStyle = DividerStyle(color = AddressSummaryConstants.dividerColor),
            editAddressButtonStyle = DefaultButtonStyle.lightSolid(
                textId = R.string.cko_edit_billing_address,
                trailingIconStyle = DefaultImageStyle.buttonTrailingImageStyle(),
                cornerRadius = CornerRadius(bottomStart = BorderConstants.radius, bottomEnd = BorderConstants.radius)
            ),
            containerStyle = ContainerStyle(
                shape = Shape.RoundCorner,
                cornerRadius = CornerRadius(BorderConstants.radius),
                borderStroke = BorderStroke(
                    BorderConstants.unfocusedBorderThickness,
                    LightStyleConstants.unfocusedBorderColor
                ),
                margin = Margin(
                    top = AddressSummaryConstants.marginBeforeSummarySection,
                    bottom = PaymentDetailsScreenConstants.marginBottom
                )
            )
        )

        return AddressSummaryComponentStyle(
            titleStyle,
            subTitleStyle,
            addAddressButtonStyle,
            summarySectionStyle,
            isOptional = isOptional
        )
    }
}

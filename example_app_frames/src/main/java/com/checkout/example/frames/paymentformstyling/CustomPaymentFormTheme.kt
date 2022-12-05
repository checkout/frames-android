package com.checkout.example.frames.paymentformstyling

import com.checkout.frames.R
import com.checkout.frames.model.CornerRadius
import com.checkout.frames.model.Shape
import com.checkout.frames.style.theme.PaymentFormThemeColors
import com.checkout.frames.style.theme.PaymentFormComponentBuilder
import com.checkout.frames.style.theme.PaymentFormComponentField
import com.checkout.frames.style.theme.PaymentFormTheme
import com.checkout.frames.style.theme.DefaultPaymentFormTheme
import com.checkout.frames.style.theme.PaymentFormShape
import com.checkout.frames.style.theme.PaymentFormCornerRadius
import com.checkout.frames.style.theme.colors.PaymentFormColors
import com.checkout.frames.style.theme.colors.PaymentFormButtonColors
import com.checkout.frames.style.theme.colors.CursorColors
import com.checkout.frames.style.theme.colors.DividerColor
import com.checkout.frames.style.theme.colors.ImageColors
import com.checkout.frames.style.theme.colors.InputFieldColors
import com.checkout.frames.style.theme.colors.TextColors

object CustomPaymentFormTheme {
    private const val surfaceColor = 0XFF00CC2D
    private const val onSurfaceColor = 0xFFFFFFFF
    private const val inverseSurfaceColor = 0XFF003300
    private const val backgroundColor = 0xFF17201E
    private const val errorColor = 0XFFFF0000
    private const val tertiaryColor = 0XFFB1B1B1
    private const val containerColor = 0XFF24302D

    private val paymentFormThemeColors = PaymentFormThemeColors(
        paymentFormColors = PaymentFormColors(background = backgroundColor),
        buttonColors = PaymentFormButtonColors(
            containerColor = surfaceColor,
            disabledContainerColor = inverseSurfaceColor,
            contentColor = onSurfaceColor,
            disabledContentColor = tertiaryColor,
        ),
        cursorColors = CursorColors(
            cursorColor = surfaceColor,
            errorCursorColor = errorColor,
            cursorHandleColor = surfaceColor,
            cursorHighlightColor = backgroundColor
        ),
        dividerColor = DividerColor(color = surfaceColor),
        imageColors = ImageColors(tinColor = surfaceColor),
        textColors = TextColors(
            titleColor = surfaceColor,
            subTitleColor = tertiaryColor,
            infoColor = tertiaryColor,
            errorColor = errorColor
        ),
        inputFieldColors = InputFieldColors(
            focusedBorderColor = surfaceColor,
            unfocusedBorderColor = tertiaryColor,
            disabledBorderColor = tertiaryColor,
            errorBorderColor = errorColor,
            inputFieldBackgroundColor = containerColor
        )
    )

    private val cardNumber = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.CardNumber)
        .setTitleTextId(R.string.cko_card_number_title)
        .setSubTitleText("Card number is required")
        .build()

    private val addressLineOne = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.AddressLineOne)
        .setTitleTextId(R.string.cko_billing_form_input_field_address_line_one)
        .setSubTitleText("Eg. street address, apartment number")
        .setInfoTextId(R.string.cko_input_field_optional_info)
        .setIsFieldOptional(true)
        .build()

    private val addBillingSummaryButton = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.AddBillingSummaryButton)
        .setIsFieldOptional(false)
        .setTitleText("Add billing address")
        .build()

    private val editBillingSummaryButton = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.EditBillingSummaryButton)
        .setTitleText("Edit billing address")
        .build()

    fun providePaymentFormTheme(): PaymentFormTheme {
        return PaymentFormTheme(
            paymentFormThemeColors = paymentFormThemeColors,
            /**
             * option 1: Use combination of default and custom components
             */
            paymentFormComponents = DefaultPaymentFormTheme.providePaymentFormComponents(
                cardNumber = cardNumber,
                addressLineOne = addressLineOne,
                addBillingSummaryButton = addBillingSummaryButton,
                editBillingSummaryButton = editBillingSummaryButton
            ),
            /**
             * option 2: Use default components
             * paymentFormComponents = DefaultPaymentFormTheme.provideComponents()
             */
            paymentFormShape = PaymentFormShape(
                inputFieldShape = Shape.RoundCorner,
                addressSummaryShape = Shape.Rectangle, buttonShape = Shape.Circle
            ),
            paymentFormCornerRadius = PaymentFormCornerRadius(
                inputFieldCornerRadius = CornerRadius(INPUT_FIELD_CORNER_RADIUS),
                addressSummaryCornerRadius = CornerRadius(INPUT_FIELD_CORNER_RADIUS)
            )
        )
    }
}

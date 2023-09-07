package com.checkout.example.frames.paymentformstyling

import com.checkout.example.frames.R
import com.checkout.frames.model.CornerRadius
import com.checkout.frames.model.Shape
import com.checkout.frames.style.theme.DefaultPaymentFormTheme
import com.checkout.frames.style.theme.PaymentFormComponentBuilder
import com.checkout.frames.style.theme.PaymentFormComponentField
import com.checkout.frames.style.theme.PaymentFormCornerRadius
import com.checkout.frames.style.theme.PaymentFormShape
import com.checkout.frames.style.theme.PaymentFormTheme
import com.checkout.frames.style.theme.PaymentFormThemeColors
import com.checkout.frames.R as FramesR

object CustomPaymentFormTheme {
    private val paymentFormThemeColors = PaymentFormThemeColors(
        accentColor = 0XFF00CC2D,
        textColor = 0XFFB1B1B1,
        errorColor = 0XFFFF0000,
        backgroundColor = 0xFF17201E,
        fieldBackgroundColor = 0XFF24302D,
        enabledButtonColor = 0xFFFFFFFF,
        disabledButtonColor = 0XFF003300
    )

    private val cardNumber = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.CardNumber)
        .setTitleTextId(FramesR.string.cko_card_number_title)
        .setSubTitleText("Card number is required")
        .build()

    private val cardHolderName = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.CardHolderName)
        .setIsFieldOptional(false)
        .setIsFieldHidden(false)
        .setTitleTextId(FramesR.string.cko_card_holder_name_title)
        .setInfoTextId(com.checkout.example.frames.R.string.mandatory_label)
        .build()

    private val billingFormCardHolderName = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.BillingFormCardHolderName)
        .setIsFieldOptional(false)
        .setIsFieldHidden(true)
        .setTitleTextId(FramesR.string.cko_card_holder_name_title)
        .setInfoTextId(R.string.mandatory_label)
        .build()

    private val addressLineOne = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.AddressLineOne)
        .setTitleTextId(FramesR.string.cko_billing_form_input_field_address_line_one)
        .setSubTitleText("Eg. street address, apartment number")
        .setInfoTextId(FramesR.string.cko_input_field_optional_info)
        .setIsFieldOptional(true)
        .setIsFieldHidden(false)
        .build()

    private val addressLineTwo = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.AddressLineTwo)
        .setIsFieldHidden(true)
        .build()

    private val country = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.Country)
        .setIsFieldHidden(false)
        .setInfoTextId(FramesR.string.cko_input_field_optional_info)
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
                cardHolderName = cardHolderName,
                billingFormCardHolderName = billingFormCardHolderName,
                addressLineOne = addressLineOne,
                addressLineTwo = addressLineTwo,
                addBillingSummaryButton = addBillingSummaryButton,
                editBillingSummaryButton = editBillingSummaryButton,
                country = country
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

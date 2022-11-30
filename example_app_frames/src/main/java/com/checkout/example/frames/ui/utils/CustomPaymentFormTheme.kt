package com.checkout.example.frames.ui.utils

import com.checkout.frames.R
import com.checkout.frames.style.theme.PaymentFormComponentBuilder
import com.checkout.frames.style.theme.PaymentFormComponentField
import com.checkout.frames.style.theme.PaymentFormTheme
import com.checkout.frames.style.theme.PaymentFormThemeColors
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
    private const val onBackgroundColor = 0XFF24302D

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
        dividerColor = DividerColor(color = backgroundColor),
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
            inputFieldBackgroundColor = onBackgroundColor
        )
    )

    private val headerTitle = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.PaymentHeaderTitle)
        .setTitleTextId(R.string.cko_payment_details_title)
        .setBackIconImage(R.drawable.cko_ic_arrow_back)
        .build()

    private val cardScheme = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.CardScheme)
        .setTitleTextId(R.string.cko_accepted_cards_title)
        .build()

    private val cardNumber = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.CardNumber)
        .setIsFieldOptional(false)
        .setIsFieldHidden(false)
        .setTitleTextId(R.string.cko_card_number_title)
        .build()

    private val expiryDate = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.ExpiryDate)
        .setIsFieldOptional(false)
        .setIsFieldHidden(false)
        .setTitleTextId(R.string.cko_expiry_date_component_title)
        .setSubTitleTextId(R.string.cko_expiry_date_component_subtitle)
        .setPlaceHolderTextId(R.string.cko_expiry_date_component_placeholder)
        .build()

    private val cvv = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.CVV)
        .setTitleTextId(R.string.cko_cvv_component_title)
        .setSubTitleTextId(R.string.cko_cvv_component_subtitle)
        .build()

    private val billingSummary = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.BillingSummary)
        .setTitleTextId(R.string.cko_billing_address)
        .setSubTitleTextId(R.string.cko_billing_address_info)
        .build()

    private val payButton = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.PaymentDetailsButton)
        .setTitleTextId(R.string.cko_pay)
        .build()

    // Billing form components
    private val billingDetailsHeader = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.BillingDetailsHeader)
        .setTitleTextId(R.string.cko_billing_address)
        .build()

    private val billingDetailsHeaderButton = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.BillingDetailsHeaderButton)
        .setTitleTextId(R.string.cko_billing_form_button_save)
        .build()

    private val addressLineOne = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.AddressLineOne)
        .setTitleTextId(R.string.cko_billing_form_input_field_address_line_one)
        .setIsFieldOptional(false)
        .build()

    private val addressLineTwo = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.AddressLineTwo)
        .setTitleTextId(R.string.cko_billing_form_input_field_address_line_two)
        .setInfoTextId(R.string.cko_input_field_optional_info)
        .setIsFieldOptional(true)
        .build()

    private val city = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.City)
        .setTitleTextId(R.string.cko_billing_form_input_field_city)
        .setIsFieldOptional(false)
        .build()

    private val postCode = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.City)
        .setTitleTextId(R.string.cko_billing_form_input_field_postcode)
        .setIsFieldOptional(false)
        .build()

    private val state = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.State)
        .setTitleTextId(R.string.cko_billing_form_input_field_state)
        .setInfoTextId(R.string.cko_input_field_optional_info)
        .setIsFieldOptional(true)
        .build()

    private val country = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.Country)
        .setTitleTextId(R.string.cko_country_picker_screen_title)
        .build()

    private val countryPicker = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.CountryPicker)
        .setTitleTextId(R.string.cko_country_picker_screen_title)
        .build()

    private val phone = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.Phone)
        .setTitleTextId(R.string.cko_billing_form_input_field_phone_title)
        .setSubTitleTextId(R.string.cko_billing_form_input_field_phone_subtitle)
        .build()

    private val paymentFormComponents =
        listOf(
            cardScheme,
            headerTitle,
            cardNumber,
            expiryDate,
            cvv,
            payButton,
            billingSummary,
            billingDetailsHeader,
            country,
            countryPicker,
            addressLineOne,
            addressLineTwo,
            city,
            state,
            postCode,
            phone,
            billingDetailsHeaderButton
        )

    fun providePaymentFormTheme(): PaymentFormTheme {
        return PaymentFormTheme(paymentFormThemeColors, paymentFormComponents)
    }
}

package com.checkout.frames.style.theme

import com.checkout.frames.R

/**
 * Provide default theme with prefilled components
 */
public object DefaultPaymentFormTheme {
    /**
     * PaymentDetails components
     */
    private val paymentHeaderTitle = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.PaymentHeaderTitle)
        .setBackIconImage(R.drawable.cko_ic_arrow_back)
        .build()

    private val cardScheme = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.CardScheme)
        .build()

    private val cardNumber = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.CardNumber)
        .build()

    private val expiryDate = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.ExpiryDate)
        .build()

    private val cvv = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.CVV)
        .build()

    private val billingSummaryTextStyle = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.BillingSummaryTextStyle)
        .build()

    private val billingSummaryContainer = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.BillingSummaryContainer)
        .build()

    private val payButton = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.PaymentDetailsButton)
        .build()

    /**
     * BillingDetails components
     */
    private val billingDetailsHeader = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.BillingDetailsHeader)
        .build()

    private val billingDetailsHeaderButton = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.BillingDetailsHeaderButton)
        .build()

    private val addressLineOne = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.AddressLineOne)
        .build()

    private val addressLineTwo = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.AddressLineTwo)
        .build()

    private val city = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.City)
        .build()

    private val postCode = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.City)
        .build()

    private val state = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.State)
        .build()

    private val country = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.Country)
        .build()

    private val countryPicker = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.CountryPicker)
        .build()

    private val phone = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.Phone)
        .build()

    private val paymentFormComponents =
        listOf(
            paymentHeaderTitle,
            cardScheme,
            cardNumber,
            expiryDate,
            cvv,
            payButton,
            billingSummaryTextStyle,
            billingSummaryContainer,
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

    public fun provideComponents(): List<PaymentFormComponent> {
        return paymentFormComponents
    }
}

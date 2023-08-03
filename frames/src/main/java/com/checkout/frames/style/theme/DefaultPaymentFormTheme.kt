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
        .setIsFieldOptional(false)
        .setIsFieldHidden(false)
        .setPaymentFormField(PaymentFormComponentField.CardNumber)
        .build()

    private val cardHolderName = PaymentFormComponentBuilder()
        .setIsFieldOptional(true)
        .setIsFieldHidden(false)
        .setPaymentFormField(PaymentFormComponentField.CardHolderName)
        .build()

    private val expiryDate = PaymentFormComponentBuilder()
        .setIsFieldOptional(false)
        .setIsFieldHidden(false)
        .setPaymentFormField(PaymentFormComponentField.ExpiryDate)
        .build()

    private val cvv = PaymentFormComponentBuilder()
        .setIsFieldOptional(false)
        .setIsFieldHidden(false)
        .setPaymentFormField(PaymentFormComponentField.CVV)
        .build()

    private val billingSummaryHeader = PaymentFormComponentBuilder()
        .setIsFieldOptional(false)
        .setIsFieldHidden(false)
        .setPaymentFormField(PaymentFormComponentField.BillingSummaryHeader)
        .build()

    private val addBillingSummaryButton = PaymentFormComponentBuilder()
        .setIsFieldOptional(false)
        .setIsFieldHidden(false)
        .setPaymentFormField(PaymentFormComponentField.AddBillingSummaryButton)
        .build()

    private val editBillingSummaryButton = PaymentFormComponentBuilder()
        .setIsFieldOptional(false)
        .setIsFieldHidden(false)
        .setPaymentFormField(PaymentFormComponentField.EditBillingSummaryButton)
        .build()

    private val billingSummaryPreview = PaymentFormComponentBuilder()
        .setIsFieldOptional(false)
        .setIsFieldHidden(false)
        .setPaymentFormField(PaymentFormComponentField.BillingSummaryPreview)
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
        .setIsFieldOptional(false)
        .setIsFieldHidden(false)
        .build()

    private val addressLineTwo = PaymentFormComponentBuilder()
        .setIsFieldOptional(true)
        .setIsFieldHidden(false)
        .setPaymentFormField(PaymentFormComponentField.AddressLineTwo)
        .build()

    private val city = PaymentFormComponentBuilder()
        .setIsFieldOptional(false)
        .setIsFieldHidden(false)
        .setPaymentFormField(PaymentFormComponentField.City)
        .build()

    private val postCode = PaymentFormComponentBuilder()
        .setIsFieldOptional(false)
        .setIsFieldHidden(false)
        .setPaymentFormField(PaymentFormComponentField.PostCode)
        .build()

    private val state = PaymentFormComponentBuilder()
        .setIsFieldOptional(true)
        .setIsFieldHidden(false)
        .setPaymentFormField(PaymentFormComponentField.State)
        .build()

    private val country = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.Country)
        .build()

    private val countryPicker = PaymentFormComponentBuilder()
        .setPaymentFormField(PaymentFormComponentField.CountryPicker)
        .build()

    private val phone = PaymentFormComponentBuilder()
        .setIsFieldOptional(false)
        .setIsFieldHidden(false)
        .setPaymentFormField(PaymentFormComponentField.Phone)
        .build()

    /**
     * This function is used for merchant to provide specific custom component for theme
     */
    @Suppress("LongParameterList")
    @JvmOverloads
    public fun providePaymentFormComponents(
        paymentHeaderTitle: PaymentFormComponent = this.paymentHeaderTitle,
        cardScheme: PaymentFormComponent = this.cardScheme,
        cardNumber: PaymentFormComponent = this.cardNumber,
        cardHolderName: PaymentFormComponent = this.cardHolderName,
        expiryDate: PaymentFormComponent = this.expiryDate,
        cvv: PaymentFormComponent = this.cvv,
        billingSummaryTextStyle: PaymentFormComponent = this.billingSummaryHeader,
        addBillingSummaryButton: PaymentFormComponent = this.addBillingSummaryButton,
        editBillingSummaryButton: PaymentFormComponent = this.editBillingSummaryButton,
        billingSummaryContainer: PaymentFormComponent = this.billingSummaryPreview,
        payButton: PaymentFormComponent = this.payButton,
        billingDetailsHeader: PaymentFormComponent = this.billingDetailsHeader,
        billingDetailsHeaderButton: PaymentFormComponent = this.billingDetailsHeaderButton,
        addressLineOne: PaymentFormComponent = this.addressLineOne,
        addressLineTwo: PaymentFormComponent = this.addressLineTwo,
        city: PaymentFormComponent = this.city,
        state: PaymentFormComponent = this.state,
        postCode: PaymentFormComponent = this.postCode,
        phone: PaymentFormComponent = this.phone,
        country: PaymentFormComponent = this.country,
        countryPicker: PaymentFormComponent = this.countryPicker
        ): List<PaymentFormComponent> {
        return listOf(
            paymentHeaderTitle,
            cardScheme,
            cardHolderName,
            cardNumber,
            expiryDate,
            cvv,
            billingSummaryTextStyle,
            addBillingSummaryButton,
            editBillingSummaryButton,
            billingSummaryContainer,
            payButton,
            billingDetailsHeader,
            billingDetailsHeaderButton,
            addressLineOne,
            addressLineTwo,
            city,
            state,
            postCode,
            phone,
            country,
            countryPicker
        )
    }

    /**
     * This function is used for merchant to provide all default components for theming
     */
    public fun provideComponents(): List<PaymentFormComponent> {
        return providePaymentFormComponents()
    }
}

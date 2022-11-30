package com.checkout.frames.style.theme

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardOptions

@Suppress("TooManyFunctions")
public class PaymentFormComponentBuilder : ObjectBuilder<PaymentFormComponent> {
    private var paymentFormComponentField: PaymentFormComponentField = PaymentFormComponentField.UNKNOWN
    public var isInputFieldOptional: Boolean = false
    private var isInputFieldHidden: Boolean = false
    private var titleText: String = ""
    private var inputFieldKeyBoardOption: KeyboardOptions = KeyboardOptions.Default

    @StringRes
    private var titleTextResourceId: Int? = null
    private var subTitleText: String = ""

    @StringRes
    private var subTitleTextResourceId: Int? = null
    private var infoText: String = ""

    @StringRes
    private var infoTextResourceId: Int? = null
    private var placeholderResourceText: String = ""

    @DrawableRes
    private var backIconImageID: Int? = null

    @DrawableRes
    private var leadingIconImageID: Int? = null

    @DrawableRes
    private var trailingIconImageID: Int? = null

    @StringRes
    private var placeholderResourceTextId: Int? = null

    public fun setPaymentFormField(paymentFormComponentField: PaymentFormComponentField): PaymentFormComponentBuilder =
        apply {
            this.paymentFormComponentField = paymentFormComponentField
        }

    public fun setIsFieldOptional(isOptional: Boolean): PaymentFormComponentBuilder = apply {
        this.isInputFieldOptional = isOptional
    }

    public fun setKeyBoardOption(keyboardOption: KeyboardOptions): PaymentFormComponentBuilder = apply {
        this.inputFieldKeyBoardOption = keyboardOption
    }

    public fun setIsFieldHidden(isHidden: Boolean): PaymentFormComponentBuilder = apply {
        this.isInputFieldHidden = isHidden
    }

    public fun setBackIconImage(imageId: Int): PaymentFormComponentBuilder = apply {
        this.backIconImageID = imageId
    }

    public fun setTrailingIconImage(imageId: Int): PaymentFormComponentBuilder = apply {
        this.trailingIconImageID = imageId
    }

    public fun setLeadingIconImage(imageId: Int): PaymentFormComponentBuilder = apply {
        this.leadingIconImageID = imageId
    }

    public fun setTitleText(title: String): PaymentFormComponentBuilder = apply {
        this.titleText = title
    }

    public fun setSubTitleText(subTitle: String): PaymentFormComponentBuilder = apply {
        this.subTitleText = subTitle
    }

    public fun setInfoText(infoText: String): PaymentFormComponentBuilder = apply {
        this.infoText = infoText
    }

    public fun setPlaceHolderText(placeHolderText: String): PaymentFormComponentBuilder = apply {
        this.placeholderResourceText = placeHolderText
    }

    public fun setTitleTextId(titleId: Int): PaymentFormComponentBuilder = apply {
        this.titleTextResourceId = titleId
    }

    public fun setSubTitleTextId(subTitleId: Int): PaymentFormComponentBuilder = apply {
        this.subTitleTextResourceId = subTitleId
    }

    public fun setInfoTextId(infoTextID: Int): PaymentFormComponentBuilder = apply {
        this.infoTextResourceId = infoTextID
    }

    public fun setPlaceHolderTextId(placeHolderTextId: Int): PaymentFormComponentBuilder = apply {
        this.placeholderResourceTextId = placeHolderTextId
    }

    override fun build(): PaymentFormComponent = PaymentFormComponent(
        paymentFormComponentField = paymentFormComponentField,
        isFieldOptional = isInputFieldOptional,
        isFieldHidden = isInputFieldHidden,
        titleText = titleText,
        titleTextId = titleTextResourceId,
        subTitleText = subTitleText,
        subTitleTextId = subTitleTextResourceId,
        infoText = infoText,
        infoTextId = infoTextResourceId,
        placeholderResourceText = placeholderResourceText,
        placeholderResourceTextId = placeholderResourceTextId,
        backIconImageResourceID = backIconImageID,
        trailingIconImageResourceID = trailingIconImageID,
        leadingIconImageResourceID = leadingIconImageID
    )
}

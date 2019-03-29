package com.checkout.sdk

import android.widget.LinearLayout
import com.checkout.sdk.core.Card
import com.checkout.sdk.models.BillingModel
import com.checkout.sdk.store.DataStore
import com.checkout.sdk.utils.PhoneUtils
import java.util.*


class FormCustomizer {

    private val mDataStore = DataStore.getInstance()

    /**
     * This method is used set the accepted card schemes
     *
     * @param cards array of accepted cards
     */
    fun setAcceptedCards(cards: List<Card>): FormCustomizer {
        mDataStore.acceptedCards = cards
        return this
    }

    /**
     * This method used to set a default country for the country
     *
     * @param country Locale representing the default country for the Spinner
     */
    fun setDefaultBillingCountry(country: Locale): FormCustomizer {
        mDataStore.customerCountry = country.country
        mDataStore.defaultCountry = country
        mDataStore.customerPhonePrefix = PhoneUtils.getPrefix(country.country)
        return this
    }

    /**
     * This method used to set a custom label for the CardholderInput
     *
     * @param label String representing the value for the Label
     */
    fun setCardHolderLabel(label: String): FormCustomizer {
        mDataStore.cardHolderLabel = label
        return this
    }

    /**
     * This method used to set a custom label for the AddressLine 1 Input
     *
     * @param label String representing the value for the Label
     */
    fun setAddress1Label(label: String): FormCustomizer {
        mDataStore.addressLine1Label = label
        return this
    }

    /**
     * This method used to set a custom label for the AddressLine 2 Input
     *
     * @param label String representing the value for the Label
     */
    fun setAddress2Label(label: String): FormCustomizer {
        mDataStore.addressLine2Label = label
        return this
    }

    /**
     * This method used to set a custom label for the StateInput
     *
     * @param label String representing the value for the Label
     */
    fun setTownLabel(label: String): FormCustomizer {
        mDataStore.townLabel = label
        return this
    }

    /**
     * This method used to set a custom label for the StateInput
     *
     * @param label String representing the value for the Label
     */
    fun setStateLabel(label: String): FormCustomizer {
        mDataStore.stateLabel = label
        return this
    }

    /**
     * This method used to set a custom label for the PostcodeInput
     *
     * @param label String representing the value for the Label
     */
    fun setPostcodeLabel(label: String): FormCustomizer {
        mDataStore.postCodeLabel = label
        return this
    }

    /**
     * This method used to set a custom label for the PhoneInput
     *
     * @param label String representing the value for the Label
     */
    fun setPhoneLabel(label: String): FormCustomizer {
        mDataStore.phoneLabel = label
        return this
    }

    /**
     * This method used to set a custom text for the Done button
     *
     * @param text String representing the text for the Button
     */
    fun setDoneButtonText(text: String): FormCustomizer {
        mDataStore.doneButtonText = text
        return this
    }

    /**
     * This method used to set a custom text for the Clear button
     *
     * @param text String representing the text for the Button
     */
    fun setClearButtonText(text: String): FormCustomizer {
        mDataStore.clearButtonText = text
        return this
    }

    /**
     * This method used to set a custom LayoutParameters for the Done button
     *
     * @param layout LayoutParameters representing the style for the Button
     */
    fun setDoneButtonLayout(layout: LinearLayout.LayoutParams): FormCustomizer {
        mDataStore.doneButtonLayout = layout
        return this
    }

    /**
     * This method used to set a custom LayoutParameters for the Clear button
     *
     * @param layout LayoutParameters representing the style for the Button
     */
    fun setClearButtonLayout(layout: LinearLayout.LayoutParams): FormCustomizer {
        mDataStore.clearButtonLayout = layout
        return this
    }

    /**
     * This method used to inject address details if they have already been collected
     *
     * @param billing BillingModel representing the value for the billing details
     */
    fun injectBilling(billing: BillingModel): FormCustomizer {
        mDataStore.isBillingCompleted = true
        mDataStore.lastBillingValidState = billing
        mDataStore.defaultBillingDetails = billing
        mDataStore.customerAddress1 = billing.addressLine1
        mDataStore.customerAddress2 = billing.addressLine2
        mDataStore.customerZipcode = billing.postcode
        mDataStore.customerCountry = billing.country
        mDataStore.customerCity = billing.city
        mDataStore.customerState = billing.state
        mDataStore.customerPhone = billing.phone.number
        mDataStore.customerPhonePrefix = billing.phone.countryCode
        return this
    }

    /**
     * This method used to inject the cardholder name if it has already been collected
     *
     * @param name String representing the value for the cardholder name
     */
    fun injectCardHolderName(name: String): FormCustomizer {
        mDataStore.customerName = name
        mDataStore.defaultCustomerName = name
        mDataStore.lastCustomerNameState = name
        return this
    }

    fun resetState() {
        mDataStore.cleanState()
        if (mDataStore != null && mDataStore.defaultBillingDetails != null) {
            mDataStore.isBillingCompleted = true
            mDataStore.lastBillingValidState = mDataStore.defaultBillingDetails
            mDataStore.customerAddress1 = mDataStore.defaultBillingDetails.addressLine1
            mDataStore.customerAddress2 = mDataStore.defaultBillingDetails.addressLine2
            mDataStore.customerZipcode = mDataStore.defaultBillingDetails.postcode
            mDataStore.customerCountry = mDataStore.defaultBillingDetails.country
            mDataStore.customerCity = mDataStore.defaultBillingDetails.city
            mDataStore.customerState = mDataStore.defaultBillingDetails.state
            mDataStore.customerPhone = mDataStore.defaultBillingDetails.phone.number
            mDataStore.customerPhonePrefix = mDataStore.defaultBillingDetails.phone.countryCode
        }
        if (mDataStore != null && mDataStore.defaultCustomerName != null) {
            mDataStore.customerName = mDataStore.defaultCustomerName
        } else {
            mDataStore.lastCustomerNameState = null
        }
        if (mDataStore != null && mDataStore.defaultCountry != null) {
            mDataStore.defaultCountry = mDataStore.defaultCountry
        }
        if (mDataStore != null && mDataStore.defaultBillingDetails != null) {
            mDataStore.isBillingCompleted = true
            mDataStore.lastBillingValidState = mDataStore.defaultBillingDetails
        } else {
            mDataStore.lastBillingValidState = null
        }
    }
}

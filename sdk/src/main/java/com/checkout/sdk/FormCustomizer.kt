package com.checkout.sdk

import com.checkout.sdk.billingdetails.model.BillingDetail
import com.checkout.sdk.billingdetails.model.BillingDetails
import com.checkout.sdk.core.Card
import com.checkout.sdk.store.DataStore
import com.checkout.sdk.store.InMemoryStore
import com.checkout.sdk.utils.PhoneUtils
import java.util.*


class FormCustomizer {

    private val mDataStore = DataStore.Factory.get()
    private val inMemoryStore = InMemoryStore.Factory.get()

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
     * This method used to inject address details if they have already been collected
     *
     * @param billing BillingModel representing the value for the billing details
     */
    fun injectBilling(billingModel: BillingModel): FormCustomizer {
        inMemoryStore.billingDetails = BillingDetails.from(billingModel)
        return this
    }

    /**
     * This method used to inject the cardholder name if it has already been collected
     *
     * @param name String representing the value for the cardholder name
     */
    fun injectCardHolderName(name: String): FormCustomizer {
        inMemoryStore.customerName = BillingDetail(name)
        return this
    }

    fun resetState() {
        mDataStore.cleanState()
        if (mDataStore != null && mDataStore.defaultBillingDetails != null) {
            mDataStore.isBillingCompleted = true
            mDataStore.lastBillingValidState = mDataStore.defaultBillingDetails
            mDataStore.customerAddress1 = mDataStore.defaultBillingDetails!!.addressOne.value
            mDataStore.customerAddress2 = mDataStore.defaultBillingDetails!!.addressTwo.value
            mDataStore.customerZipcode = mDataStore.defaultBillingDetails!!.postcode.value
            mDataStore.customerCountry = mDataStore.defaultBillingDetails!!.country
            mDataStore.customerCity = mDataStore.defaultBillingDetails!!.city.value
            mDataStore.customerState = mDataStore.defaultBillingDetails!!.state.value
            mDataStore.customerPhone = mDataStore.defaultBillingDetails!!.phone.number
            mDataStore.customerPhonePrefix = mDataStore.defaultBillingDetails!!.phone.countryCode
        }
        if (mDataStore != null && mDataStore.defaultCustomerName != null) {
            mDataStore.customerName = mDataStore.defaultCustomerName!!
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

package com.checkout.frames.screen.manager

import androidx.annotation.VisibleForTesting
import com.checkout.base.model.CardScheme
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.AddressField
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingAddress
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingFormFields
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

internal class PaymentFormStateManager(
    private val supportedCardSchemes: List<CardScheme>,
    private val billingFormFieldList: List<AddressField>
) : PaymentStateManager {

    override val isReadyTokenize: StateFlow<Boolean> = provideIsReadyTokenizeFlow()

    override val cardNumber: MutableStateFlow<String> = MutableStateFlow("")
    override val cardScheme: MutableStateFlow<CardScheme> = MutableStateFlow(CardScheme.UNKNOWN)
    override val isCardNumberValid: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val expiryDate: MutableStateFlow<String> = MutableStateFlow("")
    override val isExpiryDateValid: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val cvv: MutableStateFlow<String> = MutableStateFlow("")
    override val isCvvValid: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val billingAddress: MutableStateFlow<BillingAddress> = MutableStateFlow(BillingAddress())

    override val supportedCardSchemeList = provideCardSchemeList()

    override val billingAddressFields = provideBillingFormAddressFields()

    override val mandatoryBillingAddressFields = provideMandatoryBillingAddressFields()

    private fun provideBillingFormAddressFields(): List<AddressField> = billingFormFieldList.ifEmpty {
        BillingFormFields.fetchAllDefaultBillingFormFields()
    }

    private fun provideMandatoryBillingAddressFields(): List<AddressField> =
        BillingFormFields.fetchAllMandatoryBillingFormFields()

    @VisibleForTesting
    fun provideCardSchemeList(): List<CardScheme> = supportedCardSchemes.ifEmpty {
        CardScheme.fetchAllSupportedCardSchemes()
    }

    private fun provideIsReadyTokenizeFlow(): StateFlow<Boolean> = combine(
        isCardNumberValid,
        isExpiryDateValid,
        isCvvValid
    ) { values -> values.all { true } }
        .stateIn(MainScope(), SharingStarted.Lazily, false)
}

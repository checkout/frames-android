package com.checkout.frames.screen.manager

import androidx.annotation.VisibleForTesting
import com.checkout.base.model.CardScheme
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingAddress
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

internal class PaymentFormStateManager(
    private val supportedCardSchemes: List<CardScheme>
) : PaymentStateManager {

    override val cardNumber: MutableStateFlow<String> = MutableStateFlow("")
    override val isCardSchemeUpdated: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val cardScheme: MutableStateFlow<CardScheme> = MutableStateFlow(CardScheme.UNKNOWN)
    override val isCardNumberValid: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val expiryDate: MutableStateFlow<String> = MutableStateFlow("")
    override val isExpiryDateValid: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val cvv: MutableStateFlow<String> = MutableStateFlow("")
    override val isCvvValid: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val cardHolderName: MutableStateFlow<String> = MutableStateFlow("")
    override val isCardHolderNameValid: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val billingAddress: MutableStateFlow<BillingAddress> = MutableStateFlow(BillingAddress())
    override val isBillingAddressValid: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val isBillingAddressEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val visitedCountryPicker: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val supportedCardSchemeList = provideCardSchemeList()

    override val isReadyForTokenization: StateFlow<Boolean> = provideIsReadyTokenizeFlow()

    override fun resetPaymentState(
        isCvvValid: Boolean,
        isCardHolderNameValid: Boolean,
        isBillingAddressValid: Boolean,
        isBillingAddressEnabled: Boolean,
    ) {
        cardNumber.value = ""
        cardHolderName.value = ""
        cardScheme.value = CardScheme.UNKNOWN
        isCardNumberValid.value = false
        isCardSchemeUpdated.value = false
        expiryDate.value = ""
        isExpiryDateValid.value = false
        cvv.value = ""
        this.isCvvValid.value = isCvvValid
        this.isCardHolderNameValid.value = isCardHolderNameValid
        billingAddress.value = BillingAddress()
        visitedCountryPicker.value = false
        this.isBillingAddressValid.value = isBillingAddressValid
        this.isBillingAddressEnabled.value = isBillingAddressEnabled
    }

    @VisibleForTesting
    fun provideCardSchemeList(): List<CardScheme> = supportedCardSchemes.ifEmpty {
        CardScheme.fetchAllSupportedCardSchemes()
    }

    private fun provideIsReadyTokenizeFlow(): StateFlow<Boolean> = combine(
        isCardNumberValid,
        isExpiryDateValid,
        isCardHolderNameValid,
        isCvvValid,
        isBillingAddressValid
    ) { values -> values.all { it } }
        .stateIn(MainScope(), SharingStarted.Lazily, false)
}

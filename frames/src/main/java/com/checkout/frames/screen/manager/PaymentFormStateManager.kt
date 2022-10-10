package com.checkout.frames.screen.manager

import androidx.annotation.VisibleForTesting
import com.checkout.base.model.CardScheme
import com.checkout.base.model.Country
import com.checkout.frames.screen.billingformdetails.models.BillingForm
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.util.Locale

internal class PaymentFormStateManager(
    private val supportedCardSchemes: List<CardScheme>
) : PaymentStateManager {

    override val isReadyTokenize: StateFlow<Boolean> = provideIsReadyTokenizeFlow()

    override val cardNumber: MutableStateFlow<String> = MutableStateFlow("")
    override val cardScheme: MutableStateFlow<CardScheme> = MutableStateFlow(CardScheme.UNKNOWN)
    override val isCardNumberValid: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val expiryDate: MutableStateFlow<String> = MutableStateFlow("")
    override val isExpiryDateValid: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val cvv: MutableStateFlow<String> = MutableStateFlow("")
    override val isCvvValid: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val billingForm: MutableStateFlow<BillingForm> = MutableStateFlow(
        BillingForm(
            "",
            MutableStateFlow(Country.from(Locale.getDefault().country))
        )
    )

    override val supportedCardSchemeList = provideCardSchemeList()

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

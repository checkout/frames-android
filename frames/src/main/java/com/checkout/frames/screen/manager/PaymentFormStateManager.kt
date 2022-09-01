package com.checkout.frames.screen.manager

import com.checkout.base.model.CardScheme
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

internal class PaymentFormStateManager : PaymentStateManager {
    override val isReadyTokenize: StateFlow<Boolean> = provideIsReadyTokenizeFlow()

    override val cardNumber: MutableStateFlow<String> = MutableStateFlow("")
    override val cardScheme: MutableStateFlow<CardScheme> = MutableStateFlow(CardScheme.UNKNOWN)
    override val isCardNumberValid: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val expiryDate: MutableStateFlow<String> = MutableStateFlow("")
    override val isExpiryDateValid: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val cvv: MutableStateFlow<String> = MutableStateFlow("")
    override val isCvvValid: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private fun provideIsReadyTokenizeFlow(): StateFlow<Boolean> = combine(
        isCardNumberValid,
        isExpiryDateValid,
        isCvvValid
    ) { values -> values.all { true } }
        .stateIn(MainScope(), SharingStarted.Lazily, false)
}

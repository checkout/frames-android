package com.checkout.example.frames.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CVVTokenizationViewModel : ViewModel() {
    val isEnteredVisaCVVValid: MutableState<Boolean> = mutableStateOf(false)
    val isEnteredMaestroCVVValid: MutableState<Boolean> = mutableStateOf(false)
}

package com.checkout.sdk.uicommon

import com.checkout.sdk.store.InMemoryStore


interface TextInputStrategy {

    fun getInitialValue(): String = ""

    fun listenForRepositoryChange(listener: InMemoryStore.PhoneModelListener) = Unit

    fun textChanged(text: String)

    fun focusChanged(hasFocus: Boolean): Boolean

    fun reset()
}

package com.checkout.sdk.uicommon


interface TextInputStrategy {

    fun getInitialValue(): String = ""

    fun textChanged(text: String)

    fun focusChanged(text: String, hasFocus: Boolean): Boolean

    fun reset()
}

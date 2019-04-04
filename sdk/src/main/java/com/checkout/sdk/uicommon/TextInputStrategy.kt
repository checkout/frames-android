package com.checkout.sdk.uicommon


interface TextInputStrategy {

    fun getInitialValue(): String = ""

    fun textChanged(text: String)

    fun focusChanged(hasFocus: Boolean): Boolean

    fun reset()
}

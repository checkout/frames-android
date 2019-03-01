package com.checkout.android_sdk.Architecture


interface Presenter<in V> {

    fun start(view: V)

    fun stop()
}

package com.checkout.android_sdk.Architecture


interface UseCase<out T> {
    fun execute(): T
}

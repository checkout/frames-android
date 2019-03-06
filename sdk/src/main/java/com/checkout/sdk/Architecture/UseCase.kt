package com.checkout.sdk.Architecture


interface UseCase<out T> {
    fun execute(): T
}

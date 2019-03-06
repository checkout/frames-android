package com.checkout.sdk.architecture


interface UseCase<out T> {
    fun execute(): T
}

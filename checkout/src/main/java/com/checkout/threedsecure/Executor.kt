package com.checkout.threedsecure

public interface Executor<T> {
    public fun execute(request: T)
}

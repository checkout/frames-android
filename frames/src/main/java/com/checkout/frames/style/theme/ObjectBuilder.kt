package com.checkout.frames.style.theme

internal interface ObjectBuilder<ObjectType> {
    fun build(): ObjectType
}

package com.checkout.frames.style.theme

/**
 * ObjectBuilder to create [PaymentFormComponent] from the [PaymentFormComponentBuilder]
 */
internal interface ObjectBuilder<ObjectType> {
    fun build(): ObjectType
}

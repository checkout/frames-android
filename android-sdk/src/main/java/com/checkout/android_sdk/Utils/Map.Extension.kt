package com.checkout.android_sdk.Utils

internal fun <K : Any, V : Any> Map<K, V?>.filterNotNullValues(): Map<K, V> {
    return filterNotNullValuesTo(mutableMapOf())
}

internal fun <C : MutableMap<K, in V>, K : Any, V : Any> Map<K, V?>.filterNotNullValuesTo(destination: C): C {
    for (element in this) if (element.value != null) destination[element.key] = element.value!!
    return destination
}
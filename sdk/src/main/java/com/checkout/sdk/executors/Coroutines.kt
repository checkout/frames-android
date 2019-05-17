package com.checkout.sdk.executors

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

open class Coroutines {
    open val Main: CoroutineDispatcher = Dispatchers.Main
    open val IOScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
}


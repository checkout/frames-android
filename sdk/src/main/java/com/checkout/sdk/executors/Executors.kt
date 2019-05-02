package com.checkout.sdk.executors

import kotlinx.coroutines.*

interface Executor {
    val executor: CoroutineDispatcher
}

class MainExecutor : Executor {
    override val executor: CoroutineDispatcher
        get() = Dispatchers.Main
}

class IoExecutor : Executor {
    override val executor: CoroutineDispatcher
        get() = Dispatchers.IO
}

class Executors(val main: MainExecutor, val io: IoExecutor) {

    fun launchOnIo(function: suspend () -> Unit) {
        CoroutineScope(io.executor).launch {
            function.invoke()
        }
    }

    suspend fun backOnMain(function: () -> Unit) {
        withContext(main.executor) {
            function.invoke()
        }
    }
}


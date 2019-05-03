package com.checkout.sdk

import com.checkout.sdk.core.TestUsesCoroutines
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.mockito.BDDMockito


class ThreadsUnconfinedExtension : BeforeEachCallback {

    @Throws(Exception::class)
    override fun beforeEach(context: ExtensionContext) {
        val testCase = context.testInstance.get()
        if (testCase is TestUsesCoroutines) {
            val coroutines = testCase.getCoroutines()
            BDDMockito.given(coroutines.Main).willReturn(DISPATCHER)
            BDDMockito.given(coroutines.IOScope).willReturn(CoroutineScope(DISPATCHER))
        }
    }

    companion object {
        val DISPATCHER = Dispatchers.Unconfined
    }
}

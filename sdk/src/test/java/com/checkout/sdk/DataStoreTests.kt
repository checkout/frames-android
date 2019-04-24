package com.checkout.sdk

import com.checkout.sdk.store.DataStore
import org.junit.Assert.assertEquals
import org.junit.Test

class DataStoreTests {

    private val mDataStore = DataStore.Factory.get()

    @Test
    fun `when we get the instance for the second time it is the same instance`() {
        val getAgain = DataStore.Factory.get()
        assertEquals(mDataStore, getAgain)
    }
}

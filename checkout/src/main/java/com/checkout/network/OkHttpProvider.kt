package com.checkout.network

import com.checkout.network.extension.addLocalInterceptors
import com.checkout.network.extension.addRequestInterceptors
import com.checkout.network.extension.buildConnectionSpecs
import com.checkout.network.utils.OkHttpConstants.CALL_TIMEOUT_MS
import com.checkout.network.utils.OkHttpConstants.CONNECTION_TIMEOUT_MS
import com.checkout.network.utils.OkHttpConstants.READ_TIMEOUT_MS
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

internal object OkHttpProvider {

    fun createOkHttpClient(publicKey: String): OkHttpClient {
        return OkHttpClient.Builder()
            .connectionSpecs(buildConnectionSpecs())
            .retryOnConnectionFailure(true)
            .connectTimeout(CONNECTION_TIMEOUT_MS, TimeUnit.MILLISECONDS)
            .callTimeout(CALL_TIMEOUT_MS, TimeUnit.MILLISECONDS)
            .readTimeout(READ_TIMEOUT_MS, TimeUnit.MILLISECONDS)
            .addLocalInterceptors()
            .addRequestInterceptors(publicKey)
            .build()
    }
}

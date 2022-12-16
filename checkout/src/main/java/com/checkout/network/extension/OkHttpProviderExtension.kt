package com.checkout.network.extension

import android.util.Log
import com.checkout.eventlogger.BuildConfig
import com.checkout.network.utils.OkHttpConstants.HEADER_AUTHORIZATION
import com.checkout.network.utils.OkHttpConstants.HEADER_USER_AGENT_NAME
import com.checkout.network.utils.OkHttpConstants.HEADER_USER_AGENT_VALUE
import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

internal fun OkHttpClient.Builder.addLocalInterceptors(): OkHttpClient.Builder {
    if (BuildConfig.DEBUG) {
        addInterceptor(httpLoggingInterceptor())
    }
    return this
}

internal fun OkHttpClient.Builder.addRequestInterceptors(publicKey: String): OkHttpClient.Builder {
    return addInterceptor(createNetworkApiRequestInterceptor(publicKey))
}

private fun createNetworkApiRequestInterceptor(publicKey: String) = Interceptor { chain ->
    val requestBuilder = chain.request().newBuilder()

    with(requestBuilder) {
        addHeader(
            HEADER_AUTHORIZATION,
            publicKey
        )
        addHeader(
            HEADER_USER_AGENT_NAME,
            HEADER_USER_AGENT_VALUE
        )
    }
    chain.proceed(requestBuilder.build())
}

internal fun buildConnectionSpecs(): List<ConnectionSpec> = listOf(
    ConnectionSpec.RESTRICTED_TLS,
    ConnectionSpec.CLEARTEXT // Allow ClearText in debugBuilds
)

private fun httpLoggingInterceptor() = HttpLoggingInterceptor { message ->
    Log.d("[okHttp]", message)
}.also { it.setLevel(HttpLoggingInterceptor.Level.BODY) }

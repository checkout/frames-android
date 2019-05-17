package com.checkout.sdk.api

import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class OfflineInterceptor(private val connectivityManager: ConnectivityManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val result = connectivityManager.activeNetworkInfo?.isConnectedOrConnecting
        return when (result) {
            true -> chain.proceed(chain.request())
            else -> throw OfflineException()
        }
    }
}

class OfflineException : IOException()

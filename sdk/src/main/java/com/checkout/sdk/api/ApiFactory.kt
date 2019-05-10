package com.checkout.sdk.api

import android.content.Context
import android.net.ConnectivityManager
import com.checkout.sdk.utils.Environment
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiFactory(context: Context, private val environment: Environment) {

    private val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val client = OkHttpClient()
        .newBuilder()
        .addInterceptor(OfflineInterceptor(connectivityManager))
        .build()

    private fun retrofit(): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(environment.host)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val api: TokenApi = retrofit().create(TokenApi::class.java)

}

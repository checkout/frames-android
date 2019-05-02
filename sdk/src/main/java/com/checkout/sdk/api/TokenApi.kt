package com.checkout.sdk.api

import com.checkout.sdk.request.CardTokenizationRequest
import com.checkout.sdk.response.CardTokenizationResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface TokenApi{
    @Headers("Authorization: pk_test_6e40a700-d563-43cd-89d0-f9bb17d35e73")
    @POST("api2/v2/tokens/card")
    fun getTokenAsync(@Body cardTokenizationRequest: CardTokenizationRequest): Deferred<Response<CardTokenizationResponse>>
}

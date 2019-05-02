package com.checkout.sdk.core

import com.checkout.sdk.CheckoutClient
import com.checkout.sdk.api.OfflineException
import com.checkout.sdk.api.TokenApi
import com.checkout.sdk.executors.Coroutines
import com.checkout.sdk.models.CardModel
import com.checkout.sdk.request.CardTokenizationRequest
import com.checkout.sdk.response.CardTokenizationResponse
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response


@RunWith(MockitoJUnitRunner::class)
class RequestMakerTest {

    @Mock
    private lateinit var tokenApi: TokenApi

    @Mock
    private lateinit var coroutines: Coroutines

    @Mock
    private lateinit var tokenCallback: CheckoutClient.TokenCallback

    @Mock
    private lateinit var progressCallback: RequestMaker.ProgressCallback


    @InjectMocks
    private lateinit var requestMaker: RequestMaker

    @Test
    fun `when the request throws an exception we call that back to the token callback and we update the progress`() {
        val cardTokenisationRequest = CardTokenizationRequest("a", "b", "c", "d", "e")
        val completableDeferred = CompletableDeferred<Response<CardTokenizationResponse>>()
        completableDeferred.completeExceptionally(OfflineException())

        given(tokenApi.getTokenAsync(cardTokenisationRequest)).willReturn(completableDeferred)
        given(coroutines.Main).willReturn(Dispatchers.Unconfined)
        given(coroutines.IOScope).willReturn(CoroutineScope(Dispatchers.Unconfined))

        requestMaker.makeTokenRequest(cardTokenisationRequest)

        then(progressCallback.onProgressChanged(true))
        then(tokenCallback.onTokenResult(TokenResult.TokenResultNetworkError(OfflineException())))
        then(progressCallback.onProgressChanged(false))
    }

    @Test
    fun `when the request is successful we return the successful response and update the progress`() {
        val cardTokenisationRequest = CardTokenizationRequest("a", "b", "c", "d", "e")
        val cardTokenizationResponse = CardTokenizationResponse("1", "2", "3", "4", CardModel())
        val completableDeferred = CompletableDeferred<Response<CardTokenizationResponse>>()
        completableDeferred.complete(Response.success(cardTokenizationResponse))

        given(tokenApi.getTokenAsync(cardTokenisationRequest)).willReturn(completableDeferred)
        given(coroutines.Main).willReturn(Dispatchers.Unconfined)
        given(coroutines.IOScope).willReturn(CoroutineScope(Dispatchers.Unconfined))

        requestMaker.makeTokenRequest(cardTokenisationRequest)

        then(progressCallback.onProgressChanged(true))
        then(tokenCallback.onTokenResult(TokenResult.TokenResultSuccess(cardTokenizationResponse)))
        then(progressCallback.onProgressChanged(false))
    }

}

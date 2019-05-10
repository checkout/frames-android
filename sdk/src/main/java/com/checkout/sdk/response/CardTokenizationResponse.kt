package com.checkout.sdk.response

import com.checkout.sdk.models.CardModel

/**
 * The response model object for the card tokenisation response
 */
data class CardTokenizationResponse(val id: String?,
                                    val liveMode: String?,
                                    val created: String?,
                                    val used: String?,
                                    val card: CardModel?) : TokenResponse

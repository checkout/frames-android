package com.checkout.tokenization.response

import com.checkout.mock.GetTokenDetailsResponseTestJson
import com.checkout.tokenization.entity.AddressEntity
import com.checkout.tokenization.entity.PhoneEntity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should not be null`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GetCardTokenDetailsResponseTest {

    private lateinit var moshiClient: Moshi

    private lateinit var jsonTokenDetailsResponseAdapter: JsonAdapter<TokenDetailsResponse>

    private lateinit var jsonAddressEntityAdapter: JsonAdapter<AddressEntity>

    private lateinit var jsonPhoneEntityAdapter: JsonAdapter<PhoneEntity>

    @BeforeEach
    internal fun setUp() {
        moshiClient =
            Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()

        jsonTokenDetailsResponseAdapter = moshiClient.adapter(TokenDetailsResponse::class.java)

        jsonAddressEntityAdapter = moshiClient.adapter(AddressEntity::class.java)

        jsonPhoneEntityAdapter = moshiClient.adapter(PhoneEntity::class.java)
    }

    @Test
    fun `test JSON to GetCardTokenDetailsResponse`() {
        // Given
        val jsonString = GetTokenDetailsResponseTestJson.cardTokenDetailsResponse

        // When
        val cardTokenDetailsResponse = jsonTokenDetailsResponseAdapter.fromJson(jsonString)

        val addressEntity = jsonAddressEntityAdapter.fromJson(GetTokenDetailsResponseTestJson.addressJson)

        val phoneEntity = jsonPhoneEntityAdapter.fromJson(GetTokenDetailsResponseTestJson.phoneJson)

        // Then
        cardTokenDetailsResponse.`should not be null`()
        cardTokenDetailsResponse.type `should be equal to` "card"
        cardTokenDetailsResponse.token `should be equal to` "tok_ubfj2q76miwundwlk72vxt2i7q"
        cardTokenDetailsResponse.expiresOn `should be equal to` "2019-08-24T14:15:22Z"
        cardTokenDetailsResponse.expiryMonth `should be equal to` 6
        cardTokenDetailsResponse.expiryYear `should be equal to` 2025
        cardTokenDetailsResponse.scheme `should be equal to` "VISA"
        cardTokenDetailsResponse.last4 `should be equal to` "9996"
        cardTokenDetailsResponse.bin `should be equal to` "454347"
        cardTokenDetailsResponse.cardType `should be equal to` "Credit"
        cardTokenDetailsResponse.cardCategory `should be equal to` "Consumer"
        cardTokenDetailsResponse.issuer `should be equal to` "GOTHAM STATE BANK"
        cardTokenDetailsResponse.issuerCountry `should be equal to` "US"
        cardTokenDetailsResponse.productId `should be equal to` "F"
        cardTokenDetailsResponse.productType `should be equal to` "CLASSIC"
        cardTokenDetailsResponse.billingAddress `should be equal to` addressEntity
        cardTokenDetailsResponse.phone `should be equal to` phoneEntity
        cardTokenDetailsResponse.name `should be equal to` "Bruce Wayne"
    }
}

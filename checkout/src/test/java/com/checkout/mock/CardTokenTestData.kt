package com.checkout.mock

import com.checkout.base.model.Country
import com.checkout.tokenization.entity.AddressEntity
import com.checkout.tokenization.entity.PhoneEntity
import com.checkout.tokenization.model.Address
import com.checkout.tokenization.model.Phone
import com.checkout.tokenization.model.ExpiryDate
import com.checkout.tokenization.model.Card
import com.checkout.tokenization.response.TokenDetailsResponse
import com.checkout.validation.model.AddressValidationRequest

internal object CardTokenTestData {

    val addressEntity = AddressEntity(
        "Checkout.com",
        "90 Tottenham Court Road",
        "London",
        "London",
        "W1T 4TJ",
        "GB"
    )
    private val country = Country.from("GB")

    private val invalidCountry = Country.from("INVALID")

    val address = Address(
        "Checkout.com",
        "90 Tottenham Court Road",
        "London",
        "London",
        "W1T 4TJ",
        country
    )

    val addressValidationRequest = AddressValidationRequest(
        "Checkout.com",
        "90 Tottenham Court Road",
        "London",
        "London",
        "W1T 4TJ",
        country
    )

    val phoneEntity = PhoneEntity("4155552671", "44")

    val phone = Phone("4155552671", country)

    private val expiryDate = ExpiryDate(10, 25)

    val card = Card(
        expiryDate,
        "Bob martin",
        "4242424242424242",
        "123",
        address,
        phone
    )

    fun tokenDetailsResponse() = TokenDetailsResponse(
        type = "card",
        token = "tok_test",
        expiresOn = "2019-08-24T14:15:22Z",
        expiryMonth = 6,
        expiryYear = 2025,
        scheme = "VISA",
        last4 = "4242",
        bin = "454347",
        cardType = "Credit",
        cardCategory = "Consumer",
        issuer = "GOTHAM STATE BANK",
        issuerCountry = "US",
        productId = "F",
        productType = "CLASSIC",
        billingAddress = null,
        phone = null,
        name = "Bruce Wayne"
    )

    val invalidAddress = Address(
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem" +
                " Ipsum has been the" +
                " industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type" +
                " and " +
                "scrambled it to make a type specimen book. It has survived not only five centuries, but also" +
                " the leap" +
                " into electronic typesetting, remaining essentially unchanged. It was popularised" +
                " in the 1960s with the" +
                " release of Letraset sheets containing Lorem Ipsum passages, " +
                "and more recently with desktop publishing" +
                " software like Aldus PageMaker including versions of Lorem Ipsum.\n",
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem\" +\n" +
                "\" Ipsum has been the\" +\n" +
                "\" industry's standard dummy text ever since the 1500s, when an unknown printer took a" +
                " galley of type\" +\n" +
                "\" and \" +\n" +
                "\"scrambled it to make a type specimen book. It has survived not only five centuries, but also\" +\n" +
                "\" the leap\" +\n" +
                "\" into electronic typesetting, remaining essentially unchanged. It was popularised\" +\n" +
                "\" in the 1960s with the\" +\n" +
                "\" release of Letraset sheets containing Lorem Ipsum passages, \" +\n" +
                "\"and more recently with desktop publishing\" +\n" +
                "\" software like Aldus PageMaker including versions of Lorem Ipsum.\\n\"",
        "dummyLongCity????????????????????????????????????????",
        "dummyLongCity???????????????????????????????????sdds",
        "dummyLongCity???????????????????????????????????sdds",
        invalidCountry
    )

    val inValidPhone = Phone("412", invalidCountry)
}

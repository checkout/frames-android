package com.checkout.mock

internal object GetTokenDetailsResponseTestJson {

    const val addressJson = "{\n" +
            "                        \"address_line1\": \"Checkout.com\",\n" +
            "                        \"address_line2\": \"90 Tottenham Court Road\",\n" +
            "                        \"city\": \"London\",\n" +
            "                        \"state\": \"London\",\n" +
            "                        \"zip\": \"W1T 4TJ\",\n" +
            "                        \"country\": \"GB\"\n" +
            "                      }"
    const val phoneJson = "{\n" +
            "                        \"number\": \"4155552671\",\n" +
            "                        \"country_code\": \"44\"\n" +
            "                      }"

    val cardTokenDetailsResponse = """
                    {
                      "type": "card",
                      "token": "tok_ubfj2q76miwundwlk72vxt2i7q",
                      "expires_on": "2019-08-24T14:15:22Z",
                      "expiry_month": 6,
                      "expiry_year": 2025,
                      "scheme": "VISA",
                      "last4": "9996",
                      "bin": "454347",
                      "card_type": "Credit",
                      "card_category": "Consumer",
                      "issuer": "GOTHAM STATE BANK",
                      "issuer_country": "US",
                      "product_id": "F",
                      "product_type": "CLASSIC",
                      "billing_address": {
                        "address_line1": "Checkout.com",
                        "address_line2": "90 Tottenham Court Road",
                        "city": "London",
                        "state": "London",
                        "zip": "W1T 4TJ",
                        "country": "GB"
                      },
                      "phone": {
                        "number": "4155552671",
                        "country_code": "44"
                      },
                      "name": "Bruce Wayne"
                    }
                     """.trimIndent()

    val googlePayTokenDetailsResponse = """
                      {
                      "type": "googlepay",
                      "token": "tok_ubfj2q76miwundwlk72vxt2i7q",
                      "expires_on": "2019-08-24T14:15:22Z",
                      "expiry_month": 6,
                      "expiry_year": 2025,
                      "scheme": "VISA",
                      "last4": "9996",
                      "bin": "454347",
                      "card_type": "Credit",
                      "card_category": "Consumer",
                      "issuer": "GOTHAM STATE BANK",
                      "issuer_country": "US",
                      "product_id": "F",
                      "product_type": "CLASSIC"
                      }
                     """.trimIndent()

    val cardTokenDetailsErrorResponse = """
                      {
                          "request_id": "745f1863-e67f-451d-ae3e-cb2050fc4640",
                          "error_type": "request_invalid",
                          "error_codes": [
                              "card_number_invalid",
                              "cvv_invalid"
                          ]
                      }
                     """.trimIndent()

    val googlePayTokenDetailsErrorResponse = """
                        {
                           "request_id":"e9945f8e-c69d-4ad6-9961-49a62c5f2a7e",
                           "error_type":"request_invalid",
                           "error_codes":[
                              "token_data_invalid"
                           ]
                        }
                     """.trimIndent()
}

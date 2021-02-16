package com.checkout.android_sdk.Request;

import com.checkout.android_sdk.Models.BillingModel;
import com.checkout.android_sdk.Models.PhoneModel;
import com.google.gson.annotations.SerializedName;

/**
 * The request model object for the card tokenisation request
 */
public class CardTokenisationRequest {

    @SerializedName("type")
    private final String type = TokenType.CARD.getValue();
    @SerializedName("number")
    private String number;
    @SerializedName("name")
    private String name;
    @SerializedName("expiry_month")
    private String expiry_month;
    @SerializedName("expiry_year")
    private String expiry_year;
    @SerializedName("cvv")
    private String cvv;
    @SerializedName("billing_address")
    private BillingModel billing_address;
    @SerializedName("phone")
    private PhoneModel phone;

    public CardTokenisationRequest(String number, String name, String expiryMonth, String expiryYear, String cvv, BillingModel billingDetails, PhoneModel phone) {
        this.number = number;
        this.name = name;
        this.expiry_month = expiryMonth;
        this.expiry_year = expiryYear;
        this.cvv = cvv;
        this.billing_address = billingDetails;
        this.phone = phone;
    }

    public CardTokenisationRequest(String number, String name, String expiryMonth, String expiryYear, String cvv) {
        this.number = number;
        this.name = name;
        this.expiry_month = expiryMonth;
        this.expiry_year = expiryYear;
        this.cvv = cvv;
    }
}
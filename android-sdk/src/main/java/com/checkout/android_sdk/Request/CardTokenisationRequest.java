package com.checkout.android_sdk.Request;

import com.checkout.android_sdk.Models.BillingModel;
import com.checkout.android_sdk.Models.PhoneModel;
import com.google.gson.annotations.SerializedName;

/**
 * The request model object for the card tokenisation request
 */
public class CardTokenisationRequest {

    private String type = "card";
    private String number;
    private String name;
    private String expiry_month;
    private String expiry_year;
    private String cvv;
    private BillingModel billing_address;
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
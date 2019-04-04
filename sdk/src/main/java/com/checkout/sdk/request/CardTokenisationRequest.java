package com.checkout.sdk.request;

import com.checkout.sdk.billingdetails.model.BillingDetails;

/**
 * The request model object for the card tokenisation request
 */
public class CardTokenisationRequest {

    private String number;
    private String name;
    private String expiryMonth;
    private String expiryYear;
    private String cvv;

    private BillingDetails billingDetails;

    public CardTokenisationRequest(String number, String name, String expiryMonth, String expiryYear, String cvv, BillingDetails billingDetails) {
        this.number = number;
        this.name = name;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.cvv = cvv;
        this.billingDetails = billingDetails;
    }

    public CardTokenisationRequest(String number, String name, String expiryMonth, String expiryYear, String cvv) {
        this.number = number;
        this.name = name;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.cvv = cvv;
    }
}

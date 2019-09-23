package com.checkout.android_sdk.Response;

/**
 * The response model object for the Google Pay tokenisation response
 */
public class GooglePayTokenisationResponse {

    private String type;
    private String token;
    private String expires_on;
    private Integer expiry_month;
    private Integer expiry_year;
    private String scheme;
    private String last4;
    private String bin;
    private String card_type;
    private String card_category;
    private String issuer;
    private String issuer_country;
    private String product_id;
    private String product_type;

    public String getType() {
        return type;
    }

    public String getToken() {
        return token;
    }

    public String getTokenExpiry() {
        return expires_on;
    }

    public Integer getCardExpiryMonth() {
        return expiry_month;
    }

    public Integer getCardExpiryYear() {
        return expiry_year;
    }

    public String getScheme() {
        return scheme;
    }

    public String getLast4() {
        return last4;
    }

    public String getBin() {
        return bin;
    }

    public String getCardType() {
        return card_type;
    }

    public String getCardCategory() {
        return card_category;
    }

    public String getIssuer() {
        return issuer;
    }

    public String getIssuerCountry() {
        return issuer_country;
    }

    public String getProductId() {
        return product_id;
    }

    public String getProductType() {
        return product_type;
    }
}

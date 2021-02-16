package com.checkout.android_sdk.Response;

import com.checkout.android_sdk.Models.BillingModel;
import com.checkout.android_sdk.Models.PhoneModel;
import com.google.gson.annotations.SerializedName;

/**
 * The response model object for the card tokenisation response
 */
public class CardTokenisationResponse implements TokenisationResponse {

    @SerializedName("type")
    private String type;
    @SerializedName("token")
    private String token;
    @SerializedName("expires_on")
    private String expires_on;
    @SerializedName("expiry_month")
    private Integer expiry_month;
    @SerializedName("expiry_year")
    private Integer expiry_year;
    @SerializedName("scheme")
    private String scheme;
    @SerializedName("last4")
    private String last4;
    @SerializedName("bin")
    private String bin;
    @SerializedName("card_type")
    private String card_type;
    @SerializedName("card_category")
    private String card_category;
    @SerializedName("issuer")
    private String issuer;
    @SerializedName("issuer_country")
    private String issuer_country;
    @SerializedName("product_id")
    private String product_id;
    @SerializedName("product_type")
    private String product_type;
    @SerializedName("billing_address")
    private BillingModel billing_address;
    @SerializedName("phone")
    private PhoneModel phone;
    @SerializedName("name")
    private String name;

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public String getTokenExpiry() {
        return expires_on;
    }

    public Integer getCardExpiryMonth() {
        return expiry_month;
    }

    public Integer getCardExpiryYear() {
        return expiry_year;
    }

    @Override
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

    public BillingModel getBillingAddress() {
        return billing_address;
    }

    public PhoneModel getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }
}

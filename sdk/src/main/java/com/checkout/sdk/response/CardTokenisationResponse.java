package com.checkout.sdk.response;

import com.checkout.sdk.models.CardModel;

/**
 * The response model object for the card tokenisation response
 */
public class CardTokenisationResponse {

    private String id;
    private String liveMode;
    private String created;
    private String used;
    private CardModel card;

    public String getId() {
        return id;
    }

    public String getLiveMode() {
        return liveMode;
    }

    public String getCreated() {
        return created;
    }

    public String getUsed() {
        return used;
    }

    public CardModel getCard() {
        return card;
    }
}

package com.checkout.sdk;

import com.checkout.sdk.utils.CardUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardUtilsTests {

    // VISA

    @Test
    public void visa_short_isFound() {
        assertEquals("visa", CardUtils.INSTANCE.getType("4").getCardName());
    }

    @org.junit.jupiter.api.Test
    public void visa_full_isFound() {
        assertEquals("visa", CardUtils.INSTANCE.getType("4242424242424242").getCardName());
    }

    @Test
    public void visa_short_isNotValid() {
        assertEquals(false, CardUtils.INSTANCE.isValidCard("42"));
    }

    @org.junit.jupiter.api.Test
    public void visa_short_isValid() {
        assertEquals(true, CardUtils.INSTANCE.isValidCard("4242424242424242"));
    }

    @org.junit.jupiter.api.Test
    public void visa_short_isFormatted() {
        assertEquals("4242 4242", CardUtils.INSTANCE.getFormattedCardNumber("42424242"));
    }

    @Test
    public void visa_full_isFormatted() {
        assertEquals("4242 4242 4242 4242", CardUtils.INSTANCE.getFormattedCardNumber("4242424242424242"));
    }

    // MASTERCARD

    @Test
    public void mastercard_short_isFound() {
        assertEquals("mastercard", CardUtils.INSTANCE.getType("5436").getCardName());
    }

    @Test
    public void mastercard_full_isFound() {
        assertEquals("mastercard", CardUtils.INSTANCE.getType("5436031030606378").getCardName());
    }

    @org.junit.jupiter.api.Test
    public void mastercard_short_isNotValid() {
        assertEquals(false, CardUtils.INSTANCE.isValidCard("5436"));
    }

    @Test
    public void mastercard_short_isValid() {
        assertEquals(true, CardUtils.INSTANCE.isValidCard("5436031030606378"));
    }

    @Test
    public void mastercard_short_isFormatted() {
        assertEquals("5436 0310", CardUtils.INSTANCE.getFormattedCardNumber("54360310"));
    }

    @Test
    public void mastercard_full_isFormatted() {
        assertEquals("5436 0310 3060 6378", CardUtils.INSTANCE.getFormattedCardNumber("5436031030606378"));
    }

    // AMEX

    @Test
    public void amex_short_isFound() {
        assertEquals("amex", CardUtils.INSTANCE.getType("3456").getCardName());
    }

    @Test
    public void amex_full_isFound() {
        assertEquals("amex", CardUtils.INSTANCE.getType("345678901234564").getCardName());
    }

    @Test
    public void amex_short_isNotValid() {
        assertEquals(false, CardUtils.INSTANCE.isValidCard("3456"));
    }

    @Test
    public void amex_short_isValid() {
        assertEquals(true, CardUtils.INSTANCE.isValidCard("345678901234564"));
    }

    @Test
    public void amex_short_isFormatted() {
        assertEquals("3782 822463", CardUtils.INSTANCE.getFormattedCardNumber("3782822463"));
    }

    @Test
    public void amex_full_isFormatted() {
        assertEquals("3782 822463 10005", CardUtils.INSTANCE.getFormattedCardNumber("378282246310005"));
    }

    // DINERSCLUB

    @Test
    public void diners_short_isFound() {
        assertEquals("dinersclub", CardUtils.INSTANCE.getType("301234").getCardName());
    }

    @Test
    public void diners_full_isFound() {
        assertEquals("dinersclub", CardUtils.INSTANCE.getType("30123456789019").getCardName());
    }

    @Test
    public void diners_short_isNotValid() {
        assertEquals(false, CardUtils.INSTANCE.isValidCard("301234"));
    }

    @Test
    public void diners_short_isValid() {
        assertEquals(true, CardUtils.INSTANCE.isValidCard("30123456789019"));
    }

    @Test
    public void diners_short_isFormatted() {
        assertEquals("3012 345678", CardUtils.INSTANCE.getFormattedCardNumber("3012345678"));
    }

    @Test
    public void diners_full_isFormatted() {
        assertEquals("3012 345678 9019", CardUtils.INSTANCE.getFormattedCardNumber("30123456789019"));
    }

    // DISCOVER

    @Test
    public void discover_short_isFound() {
        assertEquals("discover", CardUtils.INSTANCE.getType("60111111").getCardName());
    }

    @Test
    public void discover_full_isFound() {
        assertEquals("discover", CardUtils.INSTANCE.getType("6011111111111117").getCardName());
    }

    @Test
    public void discover_short_isNotValid() {
        assertEquals(false, CardUtils.INSTANCE.isValidCard("60111111"));
    }

    @Test
    public void discover_short_isValid() {
        assertEquals(true, CardUtils.INSTANCE.isValidCard("6011111111111117"));
    }

    @Test
    public void discover_short_isFormatted() {
        assertEquals("6011 1111", CardUtils.INSTANCE.getFormattedCardNumber("60111111"));
    }

    @Test
    public void discover_full_isFormatted() {
        assertEquals("6011 1111 1111 1117", CardUtils.INSTANCE.getFormattedCardNumber("6011111111111117"));
    }

    // JCB

    @Test
    public void jcb_short_isFound() {
        assertEquals("jcb", CardUtils.INSTANCE.getType("35301113").getCardName());
    }

    @Test
    public void jcb_full_isFound() {
        assertEquals("jcb", CardUtils.INSTANCE.getType("3530111333300000").getCardName());
    }

    @Test
    public void jcb_short_isNotValid() {
        assertEquals(false, CardUtils.INSTANCE.isValidCard("35301113"));
    }

    @Test
    public void jcb_short_isValid() {
        assertEquals(true, CardUtils.INSTANCE.isValidCard("3530111333300000"));
    }

    @Test
    public void jcb_short_isFormatted() {
        assertEquals("3530 1113", CardUtils.INSTANCE.getFormattedCardNumber("35301113"));
    }

    @Test
    public void jcb_full_isFormatted() {
        assertEquals("3530 1113 3330 0000", CardUtils.INSTANCE.getFormattedCardNumber("3530111333300000"));
    }

    // UNIONPAY

    @Test
    public void unionpay_short_isFound() {
        assertEquals("unionpay", CardUtils.INSTANCE.getType("621234").getCardName());
    }

    @Test
    public void unionpay_full_isFound() {
        assertEquals("unionpay", CardUtils.INSTANCE.getType("6212345678901265").getCardName());
    }

    @Test
    public void unionpay_short_isNotValid() {
        assertEquals(false, CardUtils.INSTANCE.isValidCard("621234"));
    }

    @Test
    public void unionpay_short_isValid() {
        assertEquals(true, CardUtils.INSTANCE.isValidCard("6222988812340000"));
    }

    @Test
    public void unionpay_short_isFormatted() {
        assertEquals("6212 3456", CardUtils.INSTANCE.getFormattedCardNumber("62123456"));
    }

    @Test
    public void unionpay_full_isFormatted() {
        assertEquals("6212 345678 901265", CardUtils.INSTANCE.getFormattedCardNumber("6212345678901265"));
    }

    // MAESTRO

    @Test
    public void maestro_short_isFound() {
        assertEquals("maestro", CardUtils.INSTANCE.getType("6759649826438").getCardName());
    }

    @Test
    public void maestro_full_isFound() {
        assertEquals("maestro", CardUtils.INSTANCE.getType("6759649826438453").getCardName());
    }

    @Test
    public void maestro_short_isNotValid() {
        assertEquals(false, CardUtils.INSTANCE.isValidCard("6759649"));
    }

    @Test
    public void maestro_short_isValid() {
        assertEquals(true, CardUtils.INSTANCE.isValidCard("6759649826438453"));
    }

    @Test
    public void maestro_short_isFormatted() {
        assertEquals("6759 6498", CardUtils.INSTANCE.getFormattedCardNumber("67596498"));
    }

    @Test
    public void maestro_full_isFormatted() {
        assertEquals("6759 6498 2643 8453", CardUtils.INSTANCE.getFormattedCardNumber("6759649826438453"));
    }


}

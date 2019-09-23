package com.checkout.android_sdk.Store;

import android.widget.LinearLayout;

import com.checkout.android_sdk.Models.BillingModel;
import com.checkout.android_sdk.Models.PhoneModel;
import com.checkout.android_sdk.Utils.CardUtils;
import com.checkout.android_sdk.Utils.Environment;

import java.util.Calendar;
import java.util.Locale;

/**
 * The DataStore
 * <p>
 * Used to contain state within the SDK for easy communication between custom components.
 * It is also used preserve and restore state when in case the device orientation changes.
 */
public class DataStore {

    private static DataStore INSTANCE = null;
    private String mCardNumber = "";
    private String mCardMonth;
    private String mCardYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    private String mCardCvv;
    private int mCvvLength = 4;
    private Environment environment = Environment.SANDBOX;
    private String key = null;

    private CardUtils.Cards[] acceptedCards;

    private String mSuccessUrl;
    private String mFailUrl;

    private boolean IsValidCardNumber = false;
    private boolean IsValidCardMonth = false;
    private boolean IsValidCardYear = false;
    private boolean IsValidCardCvv = false;

    private String mCustomerName = "";
    private String mDefaultCustomerName  = null;
    private String mCustomerCountry = "";
    private Locale mDefaultCountry = null;
    private String mCustomerAddress1 = "";
    private String mCustomerAddress2 = "";
    private String mCustomerCity = "";
    private String mCustomerState = "";
    private String mCustomerZipcode = "";
    private String mCustomerPhonePrefix = "";
    private String mCustomerPhone = "";

    private boolean showBilling = true;
    private boolean billingCompleted = false;

    private String mAcceptedLabel = null;
    private String mCardLabel = null;
    private String mDateLabel = null;
    private String mCvvLabel = null;

    private String mCardHolderLabel = null;
    private String mAddressLine1Label = null;
    private String mAddressLine2Label = null;
    private String mTownLabel = null;
    private String mStateLabel = null;
    private String mPostCodeLabel = null;
    private String mPhoneLabel = null;

    private String mPayButtonText = null;
    private String mDoneButtonText = null;
    private String mClearButtonText = null;

    private LinearLayout.LayoutParams mPayButtonLayout = null;
    private LinearLayout.LayoutParams mDoneButtonLayout = null;
    private LinearLayout.LayoutParams mClearButtonLayout = null;

    private BillingModel mLastBillingValidState = null;
    private PhoneModel mLastPhoneValidState = null;
    private BillingModel mDefaultBillingDetails = null;
    private PhoneModel mDefaultPhoneDetails = null;
    private String mLastCustomerNameState = null;

    protected DataStore() {
    }

    public static DataStore getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataStore();
        }
        return (INSTANCE);
    }

    public String getSuccessUrl() {
        return mSuccessUrl;
    }

    public void setSuccessUrl(String successUrl) {
        mSuccessUrl = successUrl;
    }

    public String getFailUrl() {
        return mFailUrl;
    }

    public void setFailUrl(String failUrl) {
        mFailUrl = failUrl;
    }

    public String getCardNumber() {
        return mCardNumber;
    }

    public void setCardNumber(String cardNumber) {
        mCardNumber = cardNumber;
    }

    public String getCardMonth() {
        return mCardMonth;
    }

    public void setCardMonth(String mCardMonth) {
        this.mCardMonth = mCardMonth;
    }

    public String getCardYear() {
        return mCardYear;
    }

    public void setCardYear(String cardYear) {
        mCardYear = cardYear;
    }

    public String getCardCvv() {
        return mCardCvv;
    }

    public void setCardCvv(String cardCvv) {
        mCardCvv = cardCvv;
    }

    public int getCvvLength() {
        return mCvvLength;
    }

    public void setCvvLength(int cvvLength) {
        mCvvLength = cvvLength;
    }

    public boolean isValidCardNumber() {
        return IsValidCardNumber;
    }

    public void setValidCardNumber(boolean validCardNumber) {
        IsValidCardNumber = validCardNumber;
    }

    public boolean isValidCardMonth() {
        return IsValidCardMonth;
    }

    public void setValidCardMonth(boolean validCardMonth) {
        IsValidCardMonth = validCardMonth;
    }

    public boolean isValidCardYear() {
        return IsValidCardYear;
    }

    public void setValidCardYear(boolean validCardYear) {
        IsValidCardYear = validCardYear;
    }

    public boolean isValidCardCvv() {
        return IsValidCardCvv;
    }

    public void setValidCardCvv(boolean validCardCvv) {
        IsValidCardCvv = validCardCvv;
    }

    public String getCustomerCountry() {
        return mCustomerCountry;
    }

    public void setCustomerCountry(String customerCountry) {
        mCustomerCountry = customerCountry;
    }

    public Locale getDefaultCountry() {
        return mDefaultCountry;
    }

    public void setDefaultCountry(Locale mDefaultCountry) {
        this.mDefaultCountry = mDefaultCountry;
    }

    public String getCustomerPhonePrefix() {
        return mCustomerPhonePrefix;
    }

    public void setCustomerPhonePrefix(String customerPhonePrefix) {
        mCustomerPhonePrefix = customerPhonePrefix;
    }

    public String getCustomerAddress1() {
        return mCustomerAddress1;
    }

    public void setCustomerAddress1(String customerAddress1) {
        mCustomerAddress1 = customerAddress1;
    }

    public String getCustomerAddress2() {
        return mCustomerAddress2;
    }

    public void setCustomerAddress2(String customerAddress2) {
        mCustomerAddress2 = customerAddress2;
    }

    public String getCustomerCity() {
        return mCustomerCity;
    }

    public void setCustomerCity(String customerCity) {
        mCustomerCity = customerCity;
    }

    public String getCustomerState() {
        return mCustomerState;
    }

    public void setCustomerState(String customerState) {
        mCustomerState = customerState;
    }

    public String getCustomerZipcode() {
        return mCustomerZipcode;
    }

    public void setCustomerZipcode(String customerZipcode) {
        mCustomerZipcode = customerZipcode;
    }

    public String getCustomerPhone() {
        return mCustomerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        mCustomerPhone = customerPhone;
    }

    public boolean getBillingVisibility() {
        return showBilling;
    }

    public void setShowBilling(boolean showBilling) {
        this.showBilling = showBilling;
    }

    public String getCustomerName() {
        return mCustomerName;
    }

    public void setCustomerName(String customerName) {
        mCustomerName = customerName;
    }

    public boolean isBillingCompleted() {
        return billingCompleted;
    }

    public void setBillingCompleted(boolean billingCompleted) {
        this.billingCompleted = billingCompleted;
    }

    public String getCardLabel() {
        return mCardLabel;
    }

    public void setCardLabel(String mCardLabel) {
        this.mCardLabel = mCardLabel;
    }

    public String getDateLabel() {
        return mDateLabel;
    }

    public void setDateLabel(String mDateLabel) {
        this.mDateLabel = mDateLabel;
    }

    public String getCvvLabel() {
        return mCvvLabel;
    }

    public void setCvvLabel(String mCvvLabel) {
        this.mCvvLabel = mCvvLabel;
    }

    public String getAcceptedLabel() {
        return mAcceptedLabel;
    }

    public void setAcceptedLabel(String mAcceptedLabel) {
        this.mAcceptedLabel = mAcceptedLabel;
    }

    public String getCardHolderLabel() {
        return mCardHolderLabel;
    }

    public void setCardHolderLabel(String mCardHolderLabel) {
        this.mCardHolderLabel = mCardHolderLabel;
    }

    public String getAddressLine1Label() {
        return mAddressLine1Label;
    }

    public void setAddressLine1Label(String mAddressLine1Label) {
        this.mAddressLine1Label = mAddressLine1Label;
    }

    public String getAddressLine2Label() {
        return mAddressLine2Label;
    }

    public void setAddressLine2Label(String mAddressLine2Label) {
        this.mAddressLine2Label = mAddressLine2Label;
    }

    public String getTownLabel() {
        return mTownLabel;
    }

    public void setTownLabel(String mTownLabel) {
        this.mTownLabel = mTownLabel;
    }

    public String getStateLabel() {
        return mStateLabel;
    }

    public void setStateLabel(String mStateLabel) {
        this.mStateLabel = mStateLabel;
    }

    public String getPostCodeLabel() {
        return mPostCodeLabel;
    }

    public void setPostCodeLabel(String mPostocodeLabel) {
        this.mPostCodeLabel = mPostocodeLabel;
    }

    public String getPhoneLabel() {
        return mPhoneLabel;
    }

    public void setPhoneLabel(String mPhoneLabel) {
        this.mPhoneLabel = mPhoneLabel;
    }

    public String getPayButtonText() {
        return mPayButtonText;
    }

    public void setPayButtonText(String mPayButtonText) {
        this.mPayButtonText = mPayButtonText;
    }

    public String getDoneButtonText() {
        return mDoneButtonText;
    }

    public void setDoneButtonText(String mDoneButtonText) {
        this.mDoneButtonText = mDoneButtonText;
    }

    public String getClearButtonText() {
        return mClearButtonText;
    }

    public void setClearButtonText(String mClearButtonText) {
        this.mClearButtonText = mClearButtonText;
    }

    public LinearLayout.LayoutParams getPayButtonLayout() {
        return mPayButtonLayout;
    }

    public void setPayButtonLayout(LinearLayout.LayoutParams mPayButtonLayout) {
        this.mPayButtonLayout = mPayButtonLayout;
    }

    public LinearLayout.LayoutParams getDoneButtonLayout() {
        return mDoneButtonLayout;
    }

    public void setDoneButtonLayout(LinearLayout.LayoutParams mDoneButtonLayout) {
        this.mDoneButtonLayout = mDoneButtonLayout;
    }

    public LinearLayout.LayoutParams getClearButtonLayout() {
        return mClearButtonLayout;
    }

    public void setClearButtonLayout(LinearLayout.LayoutParams mClearButtonLayout) {
        this.mClearButtonLayout = mClearButtonLayout;
    }

    public BillingModel getLastBillingValidState() {
        return mLastBillingValidState;
    }

    public void setLastBillingValidState(BillingModel mLastBillingValidState) {
        this.mLastBillingValidState = mLastBillingValidState;
    }

    public PhoneModel getLastPhoneValidState() {
        return mLastPhoneValidState;
    }

    public void setLastPhoneValidState(PhoneModel mLastPhoneValidState) {
        this.mLastPhoneValidState = mLastPhoneValidState;
    }

    public String getLastCustomerNameState() {
        return mLastCustomerNameState;
    }

    public void setLastCustomerNameState(String mLastCustomerNameState) {
        this.mLastCustomerNameState = mLastCustomerNameState;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public BillingModel getDefaultBillingDetails() {
        return mDefaultBillingDetails;
    }

    public void setDefaultBillingDetails(BillingModel mDefaultBillingDetails) {
        this.mDefaultBillingDetails = mDefaultBillingDetails;
    }

    public PhoneModel getDefaultPhoneDetails() {
        return mDefaultPhoneDetails;
    }

    public void setDefaultPhoneDetails(PhoneModel mDefaultPhoneDetails) {
        this.mDefaultPhoneDetails = mDefaultPhoneDetails;
    }

    public String getDefaultCustomerName() {
        return mDefaultCustomerName;
    }

    public void setDefaultCustomerName(String mDefaulCustomerName) {
        this.mDefaultCustomerName = mDefaulCustomerName;
    }

    public void cleanBillingData() {
        DataStore.getInstance().setCustomerCountry("");
        DataStore.getInstance().setCustomerAddress1("");
        DataStore.getInstance().setCustomerAddress2("");
        DataStore.getInstance().setCustomerCity("");
        DataStore.getInstance().setCustomerState("");
        DataStore.getInstance().setCustomerZipcode("");
        DataStore.getInstance().setCustomerPhone("");
    }

    public void cleanState() {
        this.mCardNumber = "";
        this.mCardMonth = "01";
        this.mCardYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        this.mCardCvv = "";
        this.mCvvLength = 4;

        this.IsValidCardNumber = false;
        this.IsValidCardMonth = false;
        this.IsValidCardYear = false;
        this.IsValidCardCvv = false;

        this.mCustomerName = "";
        this.mCustomerCountry = "";
        this.mCustomerAddress1 = "";
        this.mCustomerAddress2 = "";
        this.mCustomerCity = "";
        this.mCustomerState = "";
        this.mCustomerZipcode = "";
        this.mCustomerPhonePrefix = "";
        this.mCustomerPhone = "";

        this.billingCompleted = false;
    }

    public CardUtils.Cards[] getAcceptedCards() {
        return acceptedCards;
    }

    public void setAcceptedCards(CardUtils.Cards[] acceptedCards) {
        this.acceptedCards = acceptedCards;
    }
}

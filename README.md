# Frames Android
[![CircleCI](https://circleci.com/gh/checkout/frames-android/tree/master.svg?style=svg)](https://circleci.com/gh/checkout/frames-android/tree/master)
[![](https://jitpack.io/v/checkout/frames-android.svg)](https://jitpack.io/#checkout/frames-android)

## Requirements
- Android minimum SDK 16

## Demo

<img src="docs/frames-andoid-demo.gif" width="38%"/>

## Installation

```gradle
// project gradle file
allprojects {
 repositories {
  ...
  maven { url 'https://jitpack.io' }
 }
}
```
```gradle
// module gradle file
dependencies {
 implementation 'com.android.support:design:27.1.1'
 implementation 'com.google.code.gson:gson:2.8.5'
 implementation 'com.android.volley:volley:1.1.0'
 implementation 'com.github.checkout:frames-android:v2.0.6'
}
```

> You can find more about the installation [here](https://jitpack.io/#checkout/frames-android/v2.0.6)

> Please keep in mind that the Jitpack repository should to be added to the project gradle file while the dependency should be added in the module gradle file. [(see more about gradle files)](https://developer.android.com/studio/build)

## Usage

### For using the module's UI you need to do the following (see app/.. DemoActivity):
<br/>

**Step1** Add the module to your XML layout.
```xml
   <com.checkout.sdk.paymentform.PaymentForm
        android:id="@+id/checkout_card_form"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
    />
```

**Step2** Include the module in your class.
```java
   private PaymentForm mPaymentForm; // include the payment form
```

**Step3** Create a callback for the Payment Form.
```java
    private final CheckoutClient.TokenCallback callback = new CheckoutClient.TokenCallback() {

            @Override
            public void onTokenResult(TokenResult tokenResult) {
                if (tokenResult instanceof TokenResult.TokenResultSuccess) {
                    mPaymentForm.clearForm();
                    String id = ((TokenResult.TokenResultSuccess) tokenResult).getResponse().token();
                    displayMessage("Token", id);
                    Log.e("TOKEN", "Token: " + id);
                } else if (tokenResult instanceof TokenResult.TokenResultTokenizationFail) {
                    String errorCode = ((TokenResult.TokenResultTokenizationFail) tokenResult).getError().errorCode();
                    displayMessage("Token Error", errorCode);
                } else if (tokenResult instanceof TokenResult.TokenResultNetworkError) {
                    String networkError = ((TokenResult.TokenResultNetworkError) tokenResult).getException().getClass().getSimpleName();
                    displayMessage("Network Error", networkError);
                } else {
                    throw new RuntimeException("Unknown Error");
                }
            }
        };
```

**Step4** Initialise the checkout client
```java
    CheckoutClient checkoutClient = CheckoutClient.Companion.create(
                    this,
                    "pk_test_6e40a700-d563-43cd-89d0-f9bb17d35e73",
                    Environment.SANDBOX,
                    callback
            );
```

**Step5** Initialise the UI
```java
    // initialise the payment from 
    mPaymentForm = findViewById(R.id.checkout_card_form);
    mPaymentForm.initialize(checkoutClient)
```
<br/>

### For using the module without the UI you need to do the following (see tokengenerator/.. MainActivity):
<br/>

**Step1** Create a token callback.
```kotlin
   val tokenCallback = object : CheckoutClient.TokenCallback {
               override fun onTokenResult(tokenResult: TokenResult) {
                   when (tokenResult) {
                       is TokenResult.TokenResultSuccess -> setSuccessText(tokenResult)
                       is TokenResult.TokenResultTokenizationFail -> setTokenizationFail(tokenResult)
                       is TokenResult.TokenResultNetworkError -> setNetworkFail(tokenResult)
                   }
               }
          }
```

**Step2** Create a CheckoutClient
```kotlin
   val checkoutClient = CheckoutClient.create(this, KEY, Environment.SANDBOX, tokenCallback)
```

**Step3** Create a CardTokenizationRequest
```kotlin
   val cardTokenizationRequest = CardTokenizationRequest(
               "4242424242424242",
               "Jim Stynes",
               "06",
               "2020",
               "100",
               null
           )
```

**Step4** Request a token
```kotlin
   checkoutClient.requestToken(cardTokenizationRequest)

```

## Customisation Options
The module extends a **Frame Layout** so you can use XML attributes like:
```java
   android:layout_width="match_parent"
   android:layout_height="match_parent"
   android:background="@colors/your_color"
```

Moreover, the module inherits the  **Theme.AppCompat.Light.DarkActionBar** style so if you want to customise the look of the payment form you can define you own style and theme the module with it:
```xml
   <style name="YourCustomTheme" parent="Theme.AppCompat.Light.DarkActionBar">
     <!-- TOOLBAR COLOR. -->
     <item name="colorPrimary">#000000</item>
     <!--FIELDS HIGHLIGHT COLOR-->
     <item name="colorAccent">#000000</item>
     <!--PAY/DONE BUTTON COLOR-->
     <item name="colorButtonNormal">#000000</item>
     <!--HELPER LABELS AND UNSELECTED FIELD COLOR-->
     <item name="colorControlNormal">#000000</item>
     <!--FONT FAMILY-->
     <item name="android:fontFamily">@font/myFont</item>
   </style>
   ...
   <com.checkout.sdk.paymentform.PaymentForm
     android:id="@+id/checkout_card_form"
     android:layout_width="match_parent"
     android:layout_height="match_parent"
     android:theme="@style/YourCustomTheme"/>
```

If you would like to customise the helper labels of the payment form fields you can override the strings
by dropping in a new `cko_string.xml` in your project: run the `uicustomizing app` and see
 /Users/caracode/Workspace/frames-android/uicustomizing/src/main/res/values/cko_strings.xml

If you would like to allow users to input their billing details when completing the payment form, you can simply use the following method:
```kotlin
    BillingModel billingModel = new BillingModel("48 Rayfield Terrace",
                    "Burton on Thames",
                    "Norwich", "United Kingdom", "TU1 8FS",
                    "Cumbria",
                    phoneModel);
    FormCustomizer formCustomizer = new FormCustomizer()
            .injectBilling(billingModel);
    ...
    mPaymentForm.initialize(checkoutClient, formCustomizer)
```

If you want to display a limited array of accepted card types you can select them in the following way:
```kotlin
   formCustomizer.setAcceptedCards(Arrays.asList(Card.VISA, Card.MASTERCARD))
```

If you collected the customer name and you would like to pre-populate it in the billing details, you can use the following:
```kotlin
  formCustomizer.injectCardHolderName("John Doe")
```

N.B. You must call any methods on `FormCustomizer` before inflating the view (i.e. before `setContentView`)

If you want to customise the buttons in the Payment From can override the strings by adding a `cko_strings.xml` in your project (see uicustomizing app) :

## Handle 3D Secure

The module allows you to handle 3DSecure URLs within your mobile app. Here are the steps:

> When you send a 3D secure charge request from your server you will get back a 3D Secure URL. You can then pass the 3D Secure URL to the module, to handle the verification.

**Step1** Create a callback.
```java
    PaymentForm.On3DSFinished m3DSecureListener = 
           new PaymentForm.On3DSFinished() {

        @Override
        public void onSuccess(String token) {
            // success
        }

        @Override
        public void onError(String errorMessage) {
            // fail
        }
    };
```

**Step2** Pass the callback to the module and handle 3D Secure
```java
    mPaymentForm = findViewById(R.id.checkout_card_form);
    mPaymentForm.set3DSListener(m3DSecureListener); // pass the callback
    
    mPaymentForm.handle3DS(
            "https://sandbox.checkout.com/api2/v2/3ds/acs/687805", // the 3D Secure URL
            "http://example.com/success", // the Redirection URL
            "http://example.com/fail" // the Redirection Fail URL
    );
```
> Keep in mind that the Redirection and Redirection Fail URLs are set in the Checkout Hub, but they can be overwritten in the charge request sent from your server. It is important to provide the correct URLs to ensure a successful payment flow.

## Handle Google Pay: see googlePayTokenGenerator app

The module allows you to handle a Google Pay token payload and retrieve a token, that can be used to create a charge from your backend.

**Step0** Copy the Google Pay token sample code from google's website

**Step1** Create a callback.
```kotlin
     val tokenCallback = object: CheckoutClient.TokenCallback {
                 override fun onTokenResult(tokenResult: TokenResult) {
                     when (tokenResult) {
                         is TokenResult.TokenResultSuccess -> googleTokenResult.text = "Token: ${tokenResult.response.token()}"
                         is TokenResult.TokenResultTokenizationFail -> googleTokenResult.text = "Error: ${tokenResult.error.errorCode()}"
                         is TokenResult.TokenResultNetworkError -> googleTokenResult.text = "Network Error: ${tokenResult.exception.javaClass.simpleName}"
                     }
                 }
             }
```
**Step2** Use the Google Pay Token to create a GooglePayTokenizationRequest
```kotlin
    val googlePayTokenizationRequest = GooglePayTokenizationRequest(
               googlePayToken.getString("protocolVersion"),
               googlePayToken.getString("signature"),
               googlePayToken.getString("signedMessage"))
```

**Step3** Request a token
```kotlin
    val checkoutClient = CheckoutClient.create(this, "pk_test_6e40a700-d563-43cd-89d0-f9bb17d35e73", Environment.SANDBOX, tokenCallback)
    checkoutClient.requestToken(googlePayTokenizationRequest)
```



## Objects found in callbacks
#### When dealing with actions like generating a card token the callback will include the following objects.

**For success -> CardTokenisationResponse** 
<br/>
With the following getters:
```java
   response.getId();               // the card token 
   response.getLiveMode();         // environment mode
   response.getCreated();          // timestamp of creation
   response.getUsed();             // show usage
   response.getCard();             // card object containing card information and billing details
```

**For error -> CardTokenisationResponse** 
<br/>
With the following getters:
```java
   error.getEventId();           // a unique identifier for the event 
   error.getMessage();           // the error message
   error.getErrorCode();         // the error code
   error.getErrorMessageCodes(); // an array or strings with all error codes 
   error.getErrors();            // an array or strings with all error messages 
```

#### When dealing with actions like generating a token for a Google Pay payload the callback will include the following objects.

**For success -> GooglePayTokenisationResponse** 
<br/>
With the following getters:
```java
   response.getToken();      // the token
   response.getExpiration(); // the token exiration 
   response.getType();       // the token type
```

**For error -> GooglePayTokenisationFail** 
<br/>
With the following getters:
```java
   error.getRequestId();  // a unique identifier for the request 
   error.getErrorType();  // the error type
   error.getErrorCodes(); // an array of strings with all the error codes
```

## License

frames-android is released under the MIT license.

# Frames Android SDK
[![CircleCI](https://circleci.com/gh/checkout/frames-android/tree/master.svg?style=svg)](https://circleci.com/gh/checkout/frames-android/tree/master)
[![](https://jitpack.io/v/checkout/frames-android.svg)](https://jitpack.io/#checkout/frames-android)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

Start accepting online payments in just a few minutes with our Android SDK. It's quick and easy to integrate, accepts online payments from all major credit cards, and is customizable to your brand.

## Features

#### Two ways to integrate:
- [Use our native UI](https://docs.checkout.com/integrate/sdks/android-sdk#AndroidSDK-Usage(withthemodule'sUI)with-UI) and embed the fully customisable form to accept card details, customer name and billing details and exchange them for a secure token.
- [Build your own UI](https://docs.checkout.com/integrate/sdks/android-sdk#AndroidSDK-Usage(withoutthemodule'sUI)without-UI) and use the provided API to send sensitive data to the Checkout.com server and retrieve the secure token.

**Accept cards and Google Pay** easily using our UI and tokenization module to stay away from PCI-compliance.

**Handle 3D Secure verification** within a WebView.

<img src="readme-docs/frames-android-demo.gif" width="38%"/>


## Documentation

Integration guides on using the Frames Android SDK are available [on our docs website](https://docs.checkout.com/integrate/sdks/android-sdk).

- SDK API reference - [Java](https://checkout.github.io/frames-android/java/index.html) / [Kotlin](https://checkout.github.io/frames-android/kotlin/index.html)
- [Customisation](https://docs.checkout.com/integrate/sdks/android-sdk/customization-guide)
- [Handle 3D Secure](https://docs.checkout.com/integrate/sdks/android-sdk/android-sdk-reference#AndroidSDKreference-Handling3DSecure)
- [Handling Google Pay](https://docs.checkout.com/integrate/sdks/android-sdk/android-sdk-reference#AndroidSDKreference-HandlingGooglePay)
- Example projects can be found [here](https://github.com/checkout/frames-android/tree/master/demos)

## Requirements
- Android minimum SDK 21

> Compatibility verified with `targetSdk` versions 21 to 31

## Installation

Add JitPack repository to the project level `build.gradle` file:
```groovy
// project gradle file
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

Add Frames SDK dependency to the module gradle file:
```groovy
// module gradle file
dependencies {
    implementation 'com.github.checkout:frames-android:<latest_version>'
}
```


> You can find more about the installation and available versions on [![](https://jitpack.io/v/checkout/frames-android.svg)](https://jitpack.io/#checkout/frames-android)

> Please keep in mind that the Jitpack repository should to be added to the project gradle file while the dependency should be added in the module gradle file. (More about build configuration files is available [here](https://developer.android.com/studio/build))

## License

Frames Android is released under the [MIT license](LICENSE).

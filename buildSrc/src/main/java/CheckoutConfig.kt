object CheckoutConfig {
    const val version = FramesConfig.productVersion
    const val artifactId = "checkout-android"
    const val groupId = "com.checkout"

    const val pomName = "checkout-android"
    const val pomDescription =
        "Mobile payment solution offered by Checkout.com for Android applications."
    const val githubProjectName = "checkout/checkout-android"
    const val githubProjectUrl = "https://github.com/$githubProjectName"

    const val dependencyNotation = "$groupId:$artifactId:$version"
    const val pomLicenseUrl = "https://raw.githubusercontent.com/${githubProjectName}/master/LICENSE"
}

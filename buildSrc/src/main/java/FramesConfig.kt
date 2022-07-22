object FramesConfig {
    const val productVersion = "3.1.2"
    const val productArtefactId = "frames-android"
    const val productGroupId = "com.github.checkout"

    const val docsTitle = "Frames Android SDK"
    const val docsDisplayName = "Frames Android SDK from Checkout.com"

    const val pomName = "Checkout.com Frames Android SDK"
    const val pomDescription = "Card tokenisation library for Android by Checkout.com"
    const val githubProjectName = "checkout/frames-android"
    const val githubProjectUrl = "https://github.com/${githubProjectName}"

    /*
    * Set to true during Development
    *  - Uses the `android-sdk` module
    *
    * Set to false for release build testing
    *  - Uses modules from maven repositories
    */
    const val useLocalModuleDependencies = true
    const val framesAndroidDependency = "${productGroupId}:${productArtefactId}:${productVersion}"
}

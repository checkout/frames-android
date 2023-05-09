object FramesConfig {
    const val productVersion = "4.0.3-SNAPSHOT"
    const val productArtefactId = "frames-android"
    const val productGroupId = "com.github.checkout"
    const val loggingGroupId = "com.checkout"

    const val docsTitle = "Frames Android SDK"
    const val docsDisplayName = "Frames Android SDK by Checkout.com"

    const val pomName = "Checkout.com Frames Android SDK"
    const val pomDescription = "Card tokenisation library for Android by Checkout.com"
    const val githubProjectName = "checkout/frames-android"
    const val githubProjectUrl = "https://github.com/${githubProjectName}"

    const val pomLicenseUrl = "https://raw.githubusercontent.com/${githubProjectName}/master/LICENSE"

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

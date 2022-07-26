//app level config constants

object AndroidConfig {
    const val compileSdk = 32
    const val minSdk = 21
    const val targetSdk = 31

    const val androidTestInstrumentation = "androidx.test.runner.AndroidJUnitRunner"
    val junit5testInstrumentationRunnerArguments =
        mapOf("runnerBuilder" to "de.mannodermaus.junit5.AndroidJUnit5Builder")
}

object ProjectDependencies {
    const val codeQualityTools = "com.vanniktech.code.quality.tools"

    val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.gradle}"
    val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    val codeQualityToolsPlugin = "com.vanniktech:gradle-code-quality-tools-plugin:${Versions.codeQualityToolsPlugin}"
    val dokkaPlugin = "org.jetbrains.dokka:dokka-gradle-plugin:${Versions.dokka}"
}

import java.io.File
import java.io.FileInputStream
import java.util.*

//version constants for the Kotlin DSL dependencies
object Versions {
    private val versionProperties = loadVersionProperty()

    // Plugin versions
    val gradle = versionProperties["gradleVersion"]
    val kotlin = versionProperties["kotlinVersion"]
    val codeQualityToolsPlugin = versionProperties["codeQualityToolsPlugin"]
    val dokka = "1.7.20"

    // Android
    const val kotlinCoroutines = "1.6.2"
    const val jsonTest = "20180813"
    const val coreKtx = "1.2.0"
    const val appcompat = "1.3.1"
    const val constraintLayout = "2.1.1"
    const val materialDesign = "1.4.0"
    const val lifeCycle = "2.5.1"
    const val compose_compiler_ext = "1.2.0"
    const val compose_material3 = "1.0.1"
    const val compose_version = "1.2.0-rc03"
    const val compose_activity_version = "1.6.0-alpha05"
    const val compose_customview_poolingcontainer = "1.0.0"
    const val compose_viewmodel = "2.5.1"
    const val compose_navigation = "2.5.1"
    const val compose_navigation_accompanist = "0.25.1"
    const val flow_layout = "0.26.4-beta"

    // Networking Dependencies
    const val okhttp = "4.10.0"
    const val loggingInterceptor = "4.9.3"
    const val moshi = "1.13.0"

    // Unit Testing Dependencies
    const val junit5Jupiter = "5.8.0"
    const val junit4 = "4.13.2"
    const val junitVintageEngine = "5.8.2"
    const val kluent = "1.68"
    const val mockk = "1.11.0"
    const val robolectric = "4.8"
    const val truth = "1.1.5"
    const val jacoco = "0.8.10"

    // Instrumented Testing Dependencies
    const val androidxJunit = "1.1.2"
    const val androidJunit5 = "1.4.0"
    const val mannodermausAndroidJunit5 = "1.3.0"
    const val espresso = "3.3.0"

    // Static code analyze
    const val ktlint = "0.50.0"
    const val detect = "1.20.0"

    // Logger
    const val eventLogger = "1.0.1"

    // Dependency injection
    const val dagger = "2.43.2"
}

fun loadVersionProperty(): Properties {
    val propertiesFilePath = "buildSrc/gradle.properties"
    var propertiesFile = File(propertiesFilePath)
    if (!propertiesFile.exists()) {
        // Path if running a command from within a subProject
        propertiesFile = File("../$propertiesFilePath")
    }

    if (!propertiesFile.exists()) {
        throw RuntimeException("Unable to locate properties file: $propertiesFilePath")
    }

    val prop = Properties()
    FileInputStream(propertiesFile).use { prop.load(it) }
    return prop
}

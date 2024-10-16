import org.jetbrains.kotlin.config.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.ralphevmanzano.moviescompose.network"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        val TMDB_API_KEY = "TMDB_API_KEY"
        val SHAKE_API_KEY = "SHAKE_API_KEY"

        val tmdbApiKey = System.getenv(TMDB_API_KEY) ?: getSecretFromProperties(TMDB_API_KEY)
        val shakeApiKey = System.getenv(SHAKE_API_KEY) ?: getSecretFromProperties(SHAKE_API_KEY)


        buildConfigField("String", TMDB_API_KEY, "\"$tmdbApiKey\"")
        buildConfigField("String", SHAKE_API_KEY, "\"$shakeApiKey\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JvmTarget.JVM_17.toString()
    }
    buildFeatures {
        buildConfig = true
    }
}

// Function to read the API key from 'secrets.properties' for local development
fun getSecretFromProperties(key: String): String? {
    val propertiesFile = rootProject.file("secrets.properties")
    return if (propertiesFile.exists()) {
        val properties = Properties().apply {
            load(propertiesFile.inputStream())
        }
        properties.getProperty(key)
    } else {
        val defaultPropertiesFile = rootProject.file("secrets.defaults.properties")
        if (defaultPropertiesFile.exists()) {
            val defaultProperties = Properties().apply {
                load(defaultPropertiesFile.inputStream())
            }
            defaultProperties.getProperty(key)
        }
        null
    }
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)


    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    implementation(platform(libs.retrofit.bom))
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.sandwich.retrofit)
    implementation(libs.sandwich)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp.logging.interceptor)

    implementation(libs.androidx.paging.runtime)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.shakebugs)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
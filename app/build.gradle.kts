import org.jetbrains.kotlin.config.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.ralphevmanzano.moviescompose"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ralphevmanzano.moviescompose"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

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
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    // modules
    implementation(project(":core:model"))
    implementation(project(":core:data"))
    implementation(project(":core:designsystem"))
    implementation(project(":feature:home"))
    implementation(project(":feature:details"))
    implementation(project(":feature:mylist"))
    implementation(project(":feature:search"))

    // hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    // shakebugs
    implementation(libs.shakebugs)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
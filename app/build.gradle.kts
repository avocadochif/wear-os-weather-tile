import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}

/**---------------Load certificates properties---------------**/
val properties = Properties().apply {
    load(FileInputStream(file("certificates/certificates.properties")))
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.avocadochif.wear.weather"
        minSdk = 26
        targetSdk = 32
        versionCode = 1
        versionName = "1.0.0"
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            buildConfigField("String", "APP_ID", properties["appID"].toString())
            buildConfigField("String", "BASE_URL", properties["baseURL"].toString())
        }

        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            buildConfigField("String", "APP_ID", properties["appID"].toString())
            buildConfigField("String", "BASE_URL", properties["baseURL"].toString())
        }
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    //Core
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.wear:wear:1.2.0")
    implementation("androidx.wear.tiles:tiles:1.0.1")
    implementation("androidx.wear.tiles:tiles-material:1.1.0-alpha05")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-guava:1.6.0")
    implementation("androidx.security:security-crypto:1.0.0")
    debugImplementation("androidx.wear.tiles:tiles-renderer:1.0.1")

    //Networking
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
    implementation("com.google.code.gson:gson:2.9.0")

    //Hilt
    implementation("com.google.dagger:hilt-android:2.41")
    kapt("com.google.dagger:hilt-android-compiler:2.41")
}
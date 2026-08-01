plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
    alias(libs.plugins.kotlin.android)
//    alias(libs.plugins.ksp)
}

android {
    namespace = "com.gongfu.recoverycompanion"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.gongfu.recoverycompanion"
        minSdk = 26
        targetSdk = 36
        versionCode = 3
        versionName = "0.3.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

configurations.all { // Temp fix for agp and ksp
    exclude(group = "com.intellij", module = "annotations")
}


dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    //Compose
    implementation(libs.bundles.compose)

    // Koin
    implementation(libs.bundles.koin)
    // Room database
    implementation(libs.bundles.room)
    //Extended Icons
    implementation(libs.androidx.compose.material.icons.extended)
    // Temporary kapt instead of ksp
    kapt(libs.room.compiler)
}
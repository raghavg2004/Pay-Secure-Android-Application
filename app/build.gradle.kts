plugins {
    alias(libs.plugins.android.application)
    id("realm-android") // Corrected to use the new plugin declaration syntax
}

android {
    namespace = "com.expense.moneytracker"
    compileSdk = 34

    buildFeatures {
        viewBinding = true // Correct Kotlin syntax
    }

    defaultConfig {
        applicationId = "com.expense.moneytracker"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.camera.view)
    implementation(libs.navigation.runtime)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation ("com.github.AnyChart:AnyChart-Android:1.1.5")  // AnyChart Show in Stats Fragment

    implementation ("com.airbnb.android:lottie:3.7.0")  // Lotty for splash screen animation

    implementation ("com.google.mlkit:barcode-scanning:17.0.3") // Google ML Kit Barcode Scanning from Google
    implementation ("com.google.mlkit:barcode-scanning:17.0.0")
    implementation ("androidx.camera:camera-core:1.0.0")
    implementation ("androidx.camera:camera-camera2:1.0.0")
    implementation ("androidx.camera:camera-lifecycle:1.0.0")
    implementation ("androidx.camera:camera-view:1.0.0")

    implementation ("com.google.zxing:core:3.4.1") // ZXing QR Code scanner
    implementation ("com.journeyapps:zxing-android-embedded:4.3.0")

    implementation ("androidx.biometric:biometric:1.2.0-alpha03")
    implementation ("androidx.biometric:biometric:1.1.0")
}

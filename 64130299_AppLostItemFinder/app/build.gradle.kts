plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "datnx.doan.timdothatlac"
    compileSdk = 34

    defaultConfig {
        applicationId = "datnx.doan.timdothatlac"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))
    implementation("com.google.firebase:firebase-analytics")
    // Firebase ML Kit (Object Detection & Image Labeling)
    implementation ("com.google.mlkit:image-labeling:17.0.9")
    implementation ("com.google.mlkit:object-detection:17.0.2")

    // Firebase Realtime Database hoặc Firestore
    implementation ("com.google.firebase:firebase-database")
    implementation ("com.google.firebase:firebase-firestore")

    // Firebase Authentication
    implementation ("com.google.firebase:firebase-auth")

    // Google Maps
    implementation ("com.google.android.gms:play-services-maps:18.1.0")
    implementation ("com.google.android.gms:play-services-location:21.1.0")

    implementation ("com.google.android.material:material:1.10.0")
    implementation ("org.osmdroid:osmdroid-android:6.1.11")

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
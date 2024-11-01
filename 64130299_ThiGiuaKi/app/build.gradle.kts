plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "vn.datnguyenxuanxuan.apponkiemtragiuaki"
    compileSdk = 34

    defaultConfig {
        applicationId = "vn.datnguyenxuanxuan.apponkiemtragiuaki"
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

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation("com.github.bumptech.glide:glide:4.15.0")
    implementation("com.google.code.gson:gson:2.8.8")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.appcompat:appcompat:1.6.1") // Updated to AndroidX
    implementation("androidx.activity:activity:1.7.2") // Updated to AndroidX
    implementation("androidx.constraintlayout:constraintlayout:2.1.4") // Updated to AndroidX
    implementation("com.android.volley:volley:1.2.1") // Volley for networking
    implementation("androidx.recyclerview:recyclerview:1.2.1") // RecyclerView for displaying the list
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

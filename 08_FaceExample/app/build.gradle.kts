plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.dig4634.faceexample"
    compileSdk = 30

    defaultConfig {
        applicationId = "com.example.dig4634.faceexample"
        minSdk = 26
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
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
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.wearable)
    implementation(libs.play.services.wearable)
    implementation(libs.percent.layout)
    implementation(libs.legacy.support.v4)
    implementation(libs.recycler.view)
    implementation(libs.palette)
    compileOnly(libs.wearable.compileOnly)
}
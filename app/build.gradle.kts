plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.rocklass.realmeet"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.rocklass.realmeet"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {
    implementation(project(":core:navigation"))
    implementation(project(":features:capture:capture-data"))
    implementation(project(":features:capture:capture-domain"))
    implementation(project(":features:capture:capture-ui"))
    implementation(project(":features:home:home-data"))
    implementation(project(":features:home:home-domain"))
    implementation(project(":features:home:home-ui"))
    implementation(project(":features:share:share-data"))
    implementation(project(":features:share:share-domain"))
    implementation(project(":features:share:share-ui"))
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
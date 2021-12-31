plugins {
    id ("com.android.application")
    id ("kotlin-android")
    kotlin("plugin.serialization") version "1.6.0"
}
val composeVersion = rootProject.extra.get("compose_version").toString()
val navVersion = "2.4.0-rc01"
android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.yumenonaka.ymnkapp"
        minSdk = 24
        targetSdk = 31
        versionCode = 1
        versionName = "1.1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "YMNK_API_URL", "\"https://www.yumeno-naka.moe/yumeno_api\"")
            signingConfig = signingConfigs.getByName("debug")
        }

        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            buildConfigField("String", "YMNK_API_URL", "\"https://www.yumeno-naka.moe/yumeno_api\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation ("androidx.core:core-ktx:1.7.0")
    implementation ("androidx.appcompat:appcompat:1.4.0")
    implementation ("com.google.android.material:material:1.4.0")
    implementation ("androidx.compose.ui:ui:$composeVersion")
    implementation ("androidx.compose.material:material:$composeVersion")
    implementation ("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")
    implementation ("androidx.activity:activity-compose:1.4.0")
    implementation ("com.squareup.okhttp3:okhttp:4.9.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")
    implementation ("com.google.dagger:hilt-android:2.38.1")
    implementation ("com.google.accompanist:accompanist-swiperefresh:0.22.0-rc")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation ("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation (kotlin("script-runtime"))
    implementation ("androidx.annotation:annotation-experimental:1.2.0")
    // navigation
    // Java language implementation
    implementation("androidx.navigation:navigation-fragment:$navVersion")
    implementation("androidx.navigation:navigation-ui:$navVersion")
    // Kotlin
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")
    // Feature module Support
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$navVersion")
    // Testing Navigation
    androidTestImplementation("androidx.navigation:navigation-testing:$navVersion")
    // Jetpack Compose Integration
    implementation("androidx.navigation:navigation-compose:$navVersion")
}

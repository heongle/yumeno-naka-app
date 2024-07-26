import java.util.Properties
import java.io.FileInputStream

plugins {
    id ("com.android.application")
    id ("kotlin-android")
    kotlin("plugin.serialization") version "1.9.24"
}

val appVersion: String by project
val composeCompilerVersion: String by project
val composeVersion: String by project
val navVersion: String by project
val ktorVersion: String by project
val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    namespace = "com.yumenonaka.ymnkapp"
    compileSdk = 34

    androidResources {
        noCompress.add("flac")
    }

    defaultConfig {
        applicationId = "com.yumenonaka.ymnkapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = appVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release-sign") {
            if(System.getenv()["CI"] == "true") {
                keyAlias = System.getenv()["CM_KEY_ALIAS"]
                keyPassword = System.getenv()["CM_KEY_PASSWORD"]
                storeFile = file(System.getenv()["CM_KEYSTORE_PATH"]!!)
                storePassword = System.getenv()["CM_KEYSTORE_PASSWORD"]
            } else {
                keyAlias = keystoreProperties["keyAlias"]!! as String
                keyPassword = keystoreProperties["keyPassword"]!! as String
                storeFile = file(keystoreProperties["storeFile"]!! as String)
                storePassword = keystoreProperties["storePassword"]!! as String
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            buildConfigField("String", "YMNK_API_URL", "\"https://www.yumeno-naka.moe/yumeno_api\"")
            buildConfigField("String", "YMNK_CDN_URL", "\"https://cdn.yumeno-naka.moe\"")
            signingConfig = signingConfigs.getByName("release-sign")
            manifestPlaceholders["appIcon"] = "@mipmap/ic_launcher"
            manifestPlaceholders["appIconRound"] = "@mipmap/ic_launcher_round"
        }

        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            buildConfigField("String", "YMNK_API_URL", "\"https://www.yumeno-naka.moe/yumeno_api\"")
            buildConfigField("String", "YMNK_CDN_URL", "\"https://cdn.yumeno-naka.moe\"")
            manifestPlaceholders["appIcon"] = "@mipmap/ic_launcher_dev"
            manifestPlaceholders["appIconRound"] = "@mipmap/ic_launcher_dev_round"
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = composeCompilerVersion
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")
    implementation ("androidx.core:core-ktx:1.13.1")
    implementation ("androidx.appcompat:appcompat:1.7.0")
    implementation ("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.compose.material3:material3-window-size-class:1.2.1")
    implementation ("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation ("androidx.activity:activity-compose:1.9.1")
    androidTestImplementation ("androidx.test.ext:junit:1.2.1")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation ("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation ("androidx.annotation:annotation-experimental:1.4.1")
    implementation ("com.google.android.material:material:1.12.0")
    // Jetpack Compose Navigation Integration
    implementation("androidx.navigation:navigation-compose:$navVersion")
    // Testing Navigation
    androidTestImplementation("androidx.navigation:navigation-testing:$navVersion")
    testImplementation ("junit:junit:4.13.2")
    implementation (kotlin("script-runtime"))

    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation ("com.google.accompanist:accompanist-swiperefresh:0.23.1")
    // Ktor client
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
}

import java.io.FileInputStream
import java.util.Locale
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
    kotlin("plugin.serialization") version "2.2.0"
}

android {
    namespace = "com.fpstudio.stretchreminder"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.fpstudio.stretchreminder"
        minSdk = 26
        targetSdk = 36
        versionCode = 2
        versionName = "0.1.0-internal"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    flavorDimensions += "environment"
    
    productFlavors {
        create("sandbox") {
            dimension = "environment"
            buildConfigField(
                "String",
                "REVENUECAT_API_KEY",
                "\"test_BdwdCXvFTRKaBYBzcrHPtakECas\""
            )
            // AdMob Test IDs for development
            buildConfigField(
                "String",
                "ADMOB_APP_ID",
                "\"ca-app-pub-3940256099942544~3347511713\""
            )
            buildConfigField(
                "String",
                "ADMOB_REWARDED_AD_UNIT_ID",
                "\"ca-app-pub-3940256099942544/5224354917\""
            )
            manifestPlaceholders["ADMOB_APP_ID"] = "ca-app-pub-3940256099942544~3347511713"
            applicationIdSuffix = ".sandbox"
            versionNameSuffix = "-sandbox"
        }

        create("production") {
            dimension = "environment"
            buildConfigField(
                "String",
                "REVENUECAT_API_KEY",
                "\"goog_gzqcUbvZZACHIiahuanLuxvmCTJ\""
            )
            // AdMob Production IDs
            buildConfigField(
                "String",
                "ADMOB_APP_ID",
                "\"ca-app-pub-3623633830858999~7946399972\""
            )
            buildConfigField(
                "String",
                "ADMOB_REWARDED_AD_UNIT_ID",
                "\"ca-app-pub-3623633830858999/2775455031\""
            )
            manifestPlaceholders["ADMOB_APP_ID"] = "ca-app-pub-3623633830858999~7946399972"
            // No suffix for production
        }
    }

    signingConfigs {
        create("release") {
            val keystorePropertiesFile = rootProject.file("keystore.properties")
            if (keystorePropertiesFile.exists()) {
                val props = Properties()
                FileInputStream(keystorePropertiesFile).use { props.load(it) }
                keyAlias = props.getProperty("keyAlias")
                keyPassword = props.getProperty("keyPassword")
                storeFile = file(props.getProperty("storeFile"))
                storePassword = props.getProperty("storePassword")
            }
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    
    // Exclude productionDebug variant
    androidComponents {
        beforeVariants { variantBuilder ->
            if (variantBuilder.buildType == "debug" && variantBuilder.productFlavors[0].second == "production") {
                variantBuilder.enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(platform(libs.firebase.bom))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.koin.core)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization)
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.ui)
    implementation(libs.media3.datasource)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.lottie.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // ADMOD
    implementation(libs.play.services.ads)

    // Firebase
    implementation("com.google.firebase:firebase-analytics")

    // Gson
    implementation(libs.gson)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    testImplementation(libs.room.testing)

    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    // Networking
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    // Image Loading
    implementation(libs.coil.compose)

    // RevenueCat
    implementation(libs.revenuecat.purchases)
    implementation(libs.purchases.ui)

    // Reorderable
    implementation(libs.reorderable)

}

// Disable Google Services for sandbox flavor (Firebase only needed in production)
android.applicationVariants.all {
    val variant = this
    if (variant.flavorName == "sandbox") {
        variant.outputs.all {
            tasks.matching {
                it.name == "process${
                    variant.name.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.getDefault()
                        ) else it.toString()
                    }
                }GoogleServices"
            }
                .configureEach { enabled = false }
        }
    }
}
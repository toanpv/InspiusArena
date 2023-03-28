import java.util.*

plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinAndroid)
    kotlin(Plugins.kapt)
}

android {
    namespace = "vn.inspius.toanpv.arena"
    compileSdk = ProjectInfo.compileSdk
    compileSdkPreview = "UpsideDownCake"

    defaultConfig {
        applicationId = "vn.inspius.toanpv.arena"
        minSdk = ProjectInfo.minSdk
        targetSdk = ProjectInfo.targetSdk
        versionCode = 1
        versionName = "0.8"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    applicationVariants.all {
        outputs.forEach { output ->
            val apkName = "InspiusArena_${versionName}_${versionCode}_${buildType.name}.apk"
            (output as? com.android.build.gradle.internal.api.BaseVariantOutputImpl)?.apply {
                outputFileName = output.outputFileName.replace(
                    output.outputFileName, apkName
                )
            }
        }
    }

    signingConfigs {
        val keystoreProperties = Properties().apply {
            load(File("keystore.properties").reader())
        }
        getByName("debug") {
            keyAlias = keystoreProperties.getProperty("keyAlias")
            keyPassword = keystoreProperties.getProperty("keyPassword")
            storeFile = file(keystoreProperties.getProperty("storeFile"))
            storePassword = keystoreProperties.getProperty("storePassword")
        }
        create("release") {
            keyAlias = keystoreProperties.getProperty("keyAlias")
            keyPassword = keystoreProperties.getProperty("keyPassword")
            storeFile = file(keystoreProperties.getProperty("storeFile"))
            storePassword = keystoreProperties.getProperty("storePassword")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }

        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
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
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":feature:match"))
    implementation(project(":domain"))

    implementation(Dependencies.Androidx.core)
    implementation(Dependencies.Androidx.appCompat)
    implementation(Dependencies.Androidx.constraintLayout)
    implementation(Dependencies.Androidx.navigationFragment)
    implementation(Dependencies.Androidx.navigationKtx)
    implementation(Dependencies.Androidx.splashscreen)
    implementation(Dependencies.material)

    implementation(Dependencies.coil)

    testImplementation(Dependencies.jUnit)
    androidTestImplementation(Dependencies.Androidx.jUnit)
    androidTestImplementation(Dependencies.Androidx.espresso)
}
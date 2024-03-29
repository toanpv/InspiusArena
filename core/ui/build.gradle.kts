plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "vn.toanpv.sample.arena.core.ui"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(Dependencies.Androidx.core)
    implementation(Dependencies.Androidx.appCompat)
    implementation(Dependencies.Androidx.fragment)
    implementation(Dependencies.Androidx.viewModel)
    implementation(Dependencies.Androidx.liveData)
    implementation(Dependencies.Androidx.splashscreen)
    implementation(Dependencies.material)

    implementation(Dependencies.coil)

    testImplementation(Dependencies.jUnit)
    androidTestImplementation(Dependencies.Androidx.jUnit)
    androidTestImplementation(Dependencies.Androidx.espresso)

}
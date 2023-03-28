plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    kotlin(Plugins.kapt)
    id(Plugins.realmDb)
    id(Plugins.kotlinSerialization)
    id(Plugins.androidxNavSafeVarargsKotlin)
    id(Plugins.parcelize)
}

android {
    namespace = "vn.inspius.toanpv.arena.match"
    compileSdk = ProjectInfo.compileSdk

    defaultConfig {
        minSdk = ProjectInfo.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":entity"))
    implementation(project(":domain"))
    implementation(project(":core:common"))
    implementation(project(":core:ui"))

    implementation(Dependencies.Androidx.core)
    implementation(Dependencies.Androidx.appCompat)
    implementation(Dependencies.Androidx.constraintLayout)
    implementation(Dependencies.Androidx.navigationFragment)
    implementation(Dependencies.Androidx.navigationKtx)
    implementation(Dependencies.Androidx.legacySupport)
    implementation(Dependencies.Androidx.recyclerView)
    implementation(Dependencies.Androidx.viewModel)
    implementation(Dependencies.Androidx.liveData)
    implementation(Dependencies.Androidx.work)
    implementation(Dependencies.Androidx.workMultiprocess)
    implementation(Dependencies.Androidx.workTesting)
    implementation(Dependencies.Androidx.startup)
    implementation(Dependencies.Androidx.dataStore)
    implementation(Dependencies.material)

    testImplementation(Dependencies.jUnit)
    androidTestImplementation(Dependencies.Androidx.jUnit)
    androidTestImplementation(Dependencies.Androidx.espresso)

    implementation(Dependencies.coroutineAndroid)

    implementation(Dependencies.coil)
    implementation(Dependencies.kotlinSerializationJson)

    implementation(Dependencies.ktor)
    implementation(Dependencies.ktorClientCio)
    implementation(Dependencies.ktorClientLogging)
    implementation(Dependencies.ktorClientContentNegotiation)
    implementation(Dependencies.ktorClientSerialization)
    implementation(Dependencies.ktorClientResources)
    implementation(Dependencies.slf4j)

    implementation(Dependencies.koin)
    implementation(Dependencies.realmDb)

    //DK Video Player
    implementation(Dependencies.dkVideoPlayer)
    implementation(Dependencies.dkVideoPlayerController)
    implementation(Dependencies.dkVideoPlayerExoPlayer)
    implementation(Dependencies.dkVideoPlayerCache)

}

kapt {
    correctErrorTypes = true
}

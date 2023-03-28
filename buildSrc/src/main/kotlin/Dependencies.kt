object Dependencies {
    object Versions {

        object Androidx {
            const val appCompat = "1.7.0-alpha02"
            const val coreKtx = "1.10.0-rc01"
            const val constraintLayout = "2.1.4"
            const val navigation = "2.6.0-alpha08"
            const val lifecycle = "2.6.1"
            const val legacySupport = "1.0.0"
            const val recyclerview = "1.3.0"
            const val work = "2.8.1"
            const val appStartup = "1.1.0-alpha01"
            const val splashscreen = "1.1.0-alpha01"
            const val dataStore = "1.0.0"

            const val jUnit = "1.1.5"
            const val espresso = "3.5.1"
        }

        const val material = "1.9.0-beta01"

        const val jUnit = "4.13.2"
        const val mockk = "1.12.3"

        const val koinVersion = "3.3.3"
        const val coroutinesAndroid = "1.6.4"
        const val coil = "2.2.2"
        const val kotlinSerialization = "1.4.1"
        const val ktor = "2.2.4"
        const val realmDb = "1.6.0"
        const val jsr305 = "2.0.2"
        const val dkVideoPlayer = "3.3.7"
    }


    object Androidx {
        const val appCompat = "androidx.appcompat:appcompat:${Versions.Androidx.appCompat}"
        const val core = "androidx.core:core-ktx:${Versions.Androidx.coreKtx}"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.Androidx.constraintLayout}"
        const val navigationFragment =
            "androidx.navigation:navigation-fragment-ktx:${Versions.Androidx.navigation}"
        const val navigationKtx =
            "androidx.navigation:navigation-ui-ktx:${Versions.Androidx.navigation}"
        const val viewModel =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Androidx.lifecycle}"
        const val liveData =
            "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.Androidx.lifecycle}"
        const val legacySupport =
            "androidx.legacy:legacy-support-v4:${Versions.Androidx.legacySupport}"
        const val recyclerView =
            "androidx.recyclerview:recyclerview:${Versions.Androidx.recyclerview}"
        const val work = "androidx.work:work-runtime-ktx:${Versions.Androidx.work}"
        const val workMultiprocess = "androidx.work:work-multiprocess:${Versions.Androidx.work}"
        const val workTesting = "androidx.work:work-testing:${Versions.Androidx.work}"
        const val startup = "androidx.startup:startup-runtime:${Versions.Androidx.appStartup}"
        const val splashscreen = "androidx.core:core-splashscreen:${Versions.Androidx.appStartup}"
        const val dataStore =
            "androidx.datastore:datastore-preferences:${Versions.Androidx.dataStore}"

        const val jUnit = "androidx.test.ext:junit:${Versions.Androidx.jUnit}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.Androidx.espresso}"
    }

    const val material = "com.google.android.material:material:${Versions.material}"
    const val coroutineAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroid}"
    const val coroutineTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesAndroid}"

    const val mockk = "io.mockk:mockk:${Versions.mockk}"

    const val coil = "io.coil-kt:coil:${Versions.coil}"
    const val koin = "io.insert-koin:koin-android:${Versions.koinVersion}"

    const val kotlinSerializationJson =
        "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinSerialization}"

    const val ktor = "io.ktor:ktor-client-core:${Versions.ktor}"
    const val ktorClientLogging = "io.ktor:ktor-client-logging:${Versions.ktor}"
    const val slf4j = "org.slf4j:slf4j-android:1.7.36"
    const val ktorClientCio = "io.ktor:ktor-client-cio:${Versions.ktor}"
    const val ktorClientContentNegotiation =
        "io.ktor:ktor-client-content-negotiation:${Versions.ktor}"
    const val ktorClientSerialization = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"
    const val ktorClientResources = "io.ktor:ktor-client-resources:${Versions.ktor}"

    const val realmDb = "io.realm.kotlin:library-base:${Versions.realmDb}"
    const val jsr305 = "com.google.code.findbugs:jsr305:${Versions.jsr305}"

    const val dkVideoPlayer = "xyz.doikki.android.dkplayer:dkplayer-java:${Versions.dkVideoPlayer}"
    const val dkVideoPlayerController =
        "xyz.doikki.android.dkplayer:dkplayer-ui:${Versions.dkVideoPlayer}"
    const val dkVideoPlayerExoPlayer =
        "xyz.doikki.android.dkplayer:player-exo:${Versions.dkVideoPlayer}"
    const val dkVideoPlayerCache =
        "xyz.doikki.android.dkplayer:videocache:${Versions.dkVideoPlayer}"

    const val jUnit = "junit:junit:${Versions.jUnit}"

}

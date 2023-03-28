plugins {
    id(Plugins.androidApplication) version Plugins.Versions.androidLibrary apply false
    id(Plugins.androidLibrary) version Plugins.Versions.androidLibrary apply false
    id(Plugins.kotlinAndroid) version Plugins.Versions.kotlinAndroid apply false
    id(Plugins.jvm) version Plugins.Versions.jvm apply false
    id(Plugins.kotlinSerialization) version Plugins.Versions.kotlinSerialization
    id(Plugins.realmDb) version Plugins.Versions.realm apply false
    id(Plugins.androidxNavSafeVarargsKotlin) version Plugins.Versions.navSafeVarargs apply false
}
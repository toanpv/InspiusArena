package vn.toanpv.sample

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.util.DebugLogger
import com.google.android.material.color.DynamicColors
import vn.toanpv.sample.BuildConfig
import vn.toanpv.sample.arena.match.di.MatchModule

class App : Application(), ImageLoaderFactory {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
        MatchModule.initModule(this)
    }

    override fun newImageLoader(): ImageLoader {
        val builder = ImageLoader.Builder(this)
        if (BuildConfig.DEBUG)
            builder.logger(DebugLogger())
        return builder
            .crossfade(true)
            .build()
    }
}
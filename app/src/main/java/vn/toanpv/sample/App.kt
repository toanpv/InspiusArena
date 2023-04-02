package vn.toanpv.sample

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.google.android.material.color.DynamicColors
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import vn.toanpv.sample.arena.match.di.MatchModule
import vn.toanpv.sample.movies.di.MovieModule

class App : Application(), ImageLoaderFactory {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
        initKoin()
    }

    private fun initKoin() {
        GlobalContext.startKoin {
            androidLogger()
            GlobalContext.loadKoinModules(
                listOf(
                    MatchModule.initKoin(context = this@App),
                    MovieModule.initKoin(context = this@App)
                )
            )
        }
    }

    override fun newImageLoader(): ImageLoader {
        val builder = ImageLoader.Builder(this)
//        if (BuildConfig.DEBUG)
//            builder.logger(DebugLogger())
        return builder
            .crossfade(true)
            .build()
    }
}
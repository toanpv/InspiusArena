package vn.inspius.toanpv.arena.match.di

import android.content.Context
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.loadKoinModules
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import vn.inspius.toanpv.arena.match.util.LogUtils
import xyz.doikki.videoplayer.exo.ExoMediaPlayerFactory
import xyz.doikki.videoplayer.player.VideoView
import xyz.doikki.videoplayer.player.VideoViewConfig
import xyz.doikki.videoplayer.player.VideoViewManager

class MatchModule {
    companion object {
        fun initModule(context: Context) {
            VideoViewManager.setConfig(
                VideoViewConfig.newBuilder()
                    .setPlayerFactory(ExoMediaPlayerFactory.create())
                    .setEnableOrientation(true)
                    .setScreenScaleType(VideoView.SCREEN_SCALE_MATCH_PARENT)
                    .setPlayOnMobileNetwork(false)
                    .build()
            )
            ContextModule.init(context)
            startKoin {
                androidLogger()
                loadKoinModules(
                    listOf(allModules)
                )
            }
            LogUtils.d("Init Match module")
        }
    }
}

val allModules = module {
    includes(
        contextModule,
        preferencesModule,
        localModule,
        networkModule,
        repositoryModule,
        userCaseModule,
        viewModelModule
    )
}
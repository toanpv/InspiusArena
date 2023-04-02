package vn.toanpv.sample.arena.match.di

import android.content.Context
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.module.Module
import org.koin.dsl.module
import vn.toanpv.sample.arena.core.ui.util.LogUtils
import xyz.doikki.videoplayer.exo.ExoMediaPlayerFactory
import xyz.doikki.videoplayer.player.VideoView
import xyz.doikki.videoplayer.player.VideoViewConfig
import xyz.doikki.videoplayer.player.VideoViewManager

class MatchModule {
    companion object {
        fun initKoin(context: Context): Module {
            initGeneral(context)
            LogUtils.d("Init Match module")
            return allModules
        }

        fun initModule(context: Context) {
            initGeneral(context)
            GlobalContext.startKoin {
                androidLogger()
                GlobalContext.loadKoinModules(
                    listOf(allModules)
                )
            }
            LogUtils.d("Init Movie module")
            return
        }

        private fun initGeneral(context: Context) {
            ContextModule.init(context)
            VideoViewManager.setConfig(
                VideoViewConfig.newBuilder()
                    .setPlayerFactory(ExoMediaPlayerFactory.create())
                    .setEnableOrientation(true)
                    .setScreenScaleType(VideoView.SCREEN_SCALE_MATCH_PARENT)
                    .setPlayOnMobileNetwork(false)
                    .build()
            )
        }
    }
}

internal val allModules = module {
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
package vn.toanpv.sample.movies.di

import android.content.Context
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.module.Module
import org.koin.dsl.module
import vn.toanpv.sample.arena.core.ui.util.LogUtils

class MovieModule {
    companion object {
        fun initKoin(context: Context): Module {
            initGeneral(context)
            LogUtils.d("Init Movie module")
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
        }
    }
}

internal val allModules = module {
    includes(
        contextModule, networkModule, repositoryModule, userCaseModule, viewModelModule
    )
}
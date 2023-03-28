package vn.inspius.toanpv.arena.match.di

import android.content.Context
import org.koin.dsl.module

object ContextModule {

    lateinit var appContext: Context

    fun init(context: Context) {
        //check(!::appContext.isInitialized) { "Already initialized!" }

        appContext = context.applicationContext
    }
}

@JvmField
val contextModule = module {
    single { ContextModule.appContext }
}
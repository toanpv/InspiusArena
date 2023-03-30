package vn.toanpv.sample.arena.match.di

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.resources.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module
import vn.toanpv.sample.arena.match.BuildConfig
import vn.toanpv.sample.arena.match.repository.data.network.TeamKtorRemoteSource
import vn.toanpv.sample.arena.repository.data.network.TeamRemoteSource

val networkModule: Module = module {
    single {
        HttpClient(CIO) {
            engine {
                endpoint {
                    maxConnectionsPerRoute = 100
                    pipelineMaxSize = 20
                    keepAliveTime = 15000
                    connectTimeout = 15000
                    connectAttempts = 5
                }
            }
            defaultRequest {
                url("https://jmde6xvjr4.execute-api.us-east-1.amazonaws.com/")
            }
            if (BuildConfig.DEBUG)
                install(Logging) {
                    logger = Logger.DEFAULT
                    level = LogLevel.INFO
                }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(Resources)
        }
    }

    single<TeamRemoteSource> { TeamKtorRemoteSource(get()) }
}
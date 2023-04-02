package vn.toanpv.sample.arena.match.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import vn.toanpv.sample.arena.match.BuildConfig
import vn.toanpv.sample.arena.match.repository.data.network.TeamKtorRemoteSource
import vn.toanpv.sample.arena.repository.data.network.TeamRemoteSource

internal val ktorMovie = named("ktor_match_module")
val networkModule: Module = module {
    single(qualifier = ktorMovie) {
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

    single<TeamRemoteSource> { TeamKtorRemoteSource(get(ktorMovie)) }
}
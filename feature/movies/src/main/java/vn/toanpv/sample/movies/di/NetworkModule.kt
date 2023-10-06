package vn.toanpv.sample.movies.di

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
import vn.toanpv.sample.arena.core.ui.BuildConfig
import vn.toanpv.sample.movies.repository.data.network.MovieRemoteSource
import vn.toanpv.sample.movies.repository.network.OmdbKtorRemoteSource

internal val ktorMovie = named("ktor_movie_module")
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
                url("https://www.omdbapi.com/?apikey=b9bd48a6")
            }
            if (BuildConfig.DEBUG)
                install(Logging) {
                    logger = Logger.DEFAULT
                    level = LogLevel.ALL
                }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(Resources)
//            install(HttpCache) {
//                publicStorage(FileStorage((get<Context>().cacheDir.absoluteFile)))
//            }
        }
    }

    single<MovieRemoteSource> { OmdbKtorRemoteSource(get(qualifier = ktorMovie)) }
}
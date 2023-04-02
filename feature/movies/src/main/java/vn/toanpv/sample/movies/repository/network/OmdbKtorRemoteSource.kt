package vn.toanpv.sample.movies.repository.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import vn.toanpv.sample.movies.repository.network.entity.OmdbMovie
import vn.toanpv.sample.movies.repository.network.entity.OmdbResponse
import vn.toanpv.sample.movies.repository.network.entity.OmdbSearchResponse
import vn.toanpv.sample.movies.entity.Movie
import vn.toanpv.sample.movies.repository.data.network.MovieRemoteSource

class OmdbKtorRemoteSource(private val client: HttpClient) : MovieRemoteSource {
    override suspend fun searchMovies(searchTitle: String, type: String?, page: Int): List<Movie>? {
        return client.get("") {
            parameter("s", searchTitle)
            if (!type.isNullOrEmpty()) parameter("t", type)
            parameter("page", page)
        }
            .body<OmdbSearchResponse>()
            .handleResult()
            .search?.map { it.convert() }
    }

    override suspend fun getMovieDetails(title: String): Movie {
        return client.get() {
            parameter("s", title)
            parameter("plot", "full")
        }
            .body<OmdbMovie>()
            .handleResult()
            .convert()
    }

    override suspend fun getMovieDetailsById(imdbId: String): Movie? {
        return client.get() {
            parameter("i", imdbId)
            parameter("plot", "full")
        }
            .body<OmdbMovie>()
            .handleResult()
            .convert()
    }


    private fun <T : OmdbResponse> T.handleResult(): T = apply {
        if (response != "True") throw RuntimeException(error ?: "Unknown error!")
    }

    private companion object {
        const val baseUrl = "https://www.omdbapi.com/?apikey=b9bd48a6"
    }
}
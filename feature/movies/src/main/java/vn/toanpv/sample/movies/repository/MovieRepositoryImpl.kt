package vn.toanpv.sample.movies.repository

import vn.toanpv.sample.movies.entity.Movie
import vn.toanpv.sample.movies.repository.MovieRepository
import vn.toanpv.sample.movies.repository.data.network.MovieRemoteSource

class MovieRepositoryImpl(private val movieRemoteSource: MovieRemoteSource) : MovieRepository {
    override suspend fun searchMovies(searchTitle: String, type: String?, page: Int): List<Movie>? {
        return movieRemoteSource.searchMovies(searchTitle, type, page)
    }

    override suspend fun getMovieDetails(title: String): Movie? {
        return movieRemoteSource.getMovieDetails(title)
    }

    override suspend fun getMovieDetailsById(imdbId: String): Movie? {
        return movieRemoteSource.getMovieDetailsById(imdbId)
    }
}
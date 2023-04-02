package vn.toanpv.sample.movies.domain

import vn.toanpv.sample.Interact
import vn.toanpv.sample.movies.entity.Movie
import vn.toanpv.sample.movies.entity.MovieType
import vn.toanpv.sample.movies.repository.MovieRepository

interface SearchMoviesInteract : Interact<SearchMoviesInteract.Param, List<Movie>?> {

    data class Param(val searchTitle: String, val type: MovieType? = null, val page: Int = 1) :
        Interact.Param()

    override suspend fun execute(param: Param?): List<Movie>?
}

class SearchMoviesInteractImpl(private val movieRepository: MovieRepository) :
    SearchMoviesInteract {
    override suspend fun execute(param: SearchMoviesInteract.Param?): List<Movie>? {
        requireNotNull(param) { "SearchMoviesInteract.Params must not be null" }
        return movieRepository.searchMovies(param.searchTitle, param.type?.type, param.page)
    }
}
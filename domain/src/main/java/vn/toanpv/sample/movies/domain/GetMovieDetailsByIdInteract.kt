package vn.toanpv.sample.movies.domain

import vn.toanpv.sample.Interact
import vn.toanpv.sample.movies.entity.Movie
import vn.toanpv.sample.movies.repository.MovieRepository

interface GetMovieDetailsByIdInteract : Interact<GetMovieDetailsByIdInteract.Params, Movie?> {

    data class Params(val imdbId: String) : Interact.Param()

    override suspend fun execute(param: Params?): Movie?
}

class GetMovieDetailsByIdInteractImpl(private val movieRepository: MovieRepository) :
    GetMovieDetailsByIdInteract {
    override suspend fun execute(param: GetMovieDetailsByIdInteract.Params?): Movie? {
        requireNotNull(param) { "GetMovieDetailsByIdInteract.Params must not be null" }
        return movieRepository.getMovieDetailsById(param.imdbId)
    }
}
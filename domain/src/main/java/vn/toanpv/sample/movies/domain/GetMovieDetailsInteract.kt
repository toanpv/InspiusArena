package vn.toanpv.sample.movies.domain

import vn.toanpv.sample.Interact
import vn.toanpv.sample.movies.entity.Movie
import vn.toanpv.sample.movies.repository.MovieRepository

interface GetMovieDetailsInteract : Interact<GetMovieDetailsInteract.Params, Movie?> {

    data class Params(val title: String) : Interact.Param()

    override suspend fun execute(param: Params?): Movie?
}

class GetMovieDetailsInteractImpl(private val movieRepository: MovieRepository) :
    GetMovieDetailsInteract {
    override suspend fun execute(param: GetMovieDetailsInteract.Params?): Movie? {
        requireNotNull(param) { "GetMovieDetailsInteract.Params must not be null" }
        return movieRepository.getMovieDetails(param.title)
    }
}
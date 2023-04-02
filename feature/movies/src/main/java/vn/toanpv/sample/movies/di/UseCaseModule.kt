package vn.toanpv.sample.movies.di

import org.koin.dsl.module
import vn.toanpv.sample.movies.domain.GetMovieDetailsByIdInteract
import vn.toanpv.sample.movies.domain.GetMovieDetailsByIdInteractImpl
import vn.toanpv.sample.movies.domain.GetMovieDetailsInteract
import vn.toanpv.sample.movies.domain.GetMovieDetailsInteractImpl
import vn.toanpv.sample.movies.domain.SearchMoviesInteract
import vn.toanpv.sample.movies.domain.SearchMoviesInteractImpl

val userCaseModule = module {
    single<SearchMoviesInteract> { SearchMoviesInteractImpl(get()) }
    single<GetMovieDetailsByIdInteract> { GetMovieDetailsByIdInteractImpl(get()) }
    single<GetMovieDetailsInteract> { GetMovieDetailsInteractImpl(get()) }
}
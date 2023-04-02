package vn.toanpv.sample.movies.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import vn.toanpv.sample.movies.ui.detail.MovieDetailViewModel
import vn.toanpv.sample.movies.ui.movie.MovieViewModel
import vn.toanpv.sample.movies.ui.movie.model.MovieItem

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { (movie: MovieItem) -> MovieDetailViewModel(get(), get(), movie) }
}
package vn.toanpv.sample.movies.di

import org.koin.dsl.module
import vn.toanpv.sample.movies.repository.MovieRepositoryImpl
import vn.toanpv.sample.movies.repository.MovieRepository

val repositoryModule = module {
    single<MovieRepository> { MovieRepositoryImpl(get()) }
}
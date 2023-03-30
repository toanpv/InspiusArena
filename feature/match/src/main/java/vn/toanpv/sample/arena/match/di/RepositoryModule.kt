package vn.toanpv.sample.arena.match.di

import org.koin.core.module.Module
import org.koin.dsl.module
import vn.toanpv.sample.arena.match.repository.team.TeamsRepositoryImpl
import vn.toanpv.sample.arena.match.repository.team.match.MatchRepositoryImpl
import vn.toanpv.sample.arena.repository.team.TeamsRepository
import vn.toanpv.sample.arena.repository.team.match.MatchRepository

val repositoryModule: Module = module {
    single<TeamsRepository> { TeamsRepositoryImpl(get(), get()) }
    single<MatchRepository> { MatchRepositoryImpl(get(), get(), get()) }
}
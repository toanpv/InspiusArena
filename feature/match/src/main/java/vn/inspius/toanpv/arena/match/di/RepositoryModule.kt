package vn.inspius.toanpv.arena.match.di

import org.koin.core.module.Module
import org.koin.dsl.module
import vn.inspius.toanpv.arena.match.repository.team.TeamsRepositoryImpl
import vn.inspius.toanpv.arena.match.repository.team.match.MatchRepositoryImpl
import vn.inspius.toanpv.arena.repository.team.TeamsRepository
import vn.inspius.toanpv.arena.repository.team.match.MatchRepository

val repositoryModule: Module = module {
    single<TeamsRepository> { TeamsRepositoryImpl(get(), get()) }
    single<MatchRepository> { MatchRepositoryImpl(get(), get(), get()) }
}
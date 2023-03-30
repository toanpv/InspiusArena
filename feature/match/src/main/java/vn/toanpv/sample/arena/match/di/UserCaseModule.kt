package vn.toanpv.sample.arena.match.di

import org.koin.core.module.Module
import org.koin.dsl.module
import vn.toanpv.sample.arena.domain.team.GetTeamsByNameInteract
import vn.toanpv.sample.arena.domain.team.GetTeamsByNameInteractImpl
import vn.toanpv.sample.arena.domain.team.GetTeamsInteract
import vn.toanpv.sample.arena.domain.team.GetTeamsInteractImpl
import vn.toanpv.sample.arena.domain.team.match.GetMatchByTeamInteract
import vn.toanpv.sample.arena.domain.team.match.GetMatchByTeamInteractImpl
import vn.toanpv.sample.arena.domain.team.match.GetMatchesInteract
import vn.toanpv.sample.arena.domain.team.match.GetMatchesInteractImpl
import vn.toanpv.sample.arena.domain.team.match.GetPreviousMatchesInteract
import vn.toanpv.sample.arena.domain.team.match.GetPreviousMatchesInteractImpl
import vn.toanpv.sample.arena.domain.team.match.GetUpcomingMatchesInteract
import vn.toanpv.sample.arena.domain.team.match.GetUpcomingMatchesInteractImpl
import vn.toanpv.sample.arena.domain.team.match.reminder.GetRemindMatchIdsInteract
import vn.toanpv.sample.arena.domain.team.match.reminder.GetRemindMatchIdsInteractImpl
import vn.toanpv.sample.arena.domain.team.match.reminder.ToggleRemindMatchIdsInteract
import vn.toanpv.sample.arena.domain.team.match.reminder.ToggleRemindMatchIdsInteractImpl
import vn.toanpv.sample.arena.domain.team.match.reminder.UpdateRemindMatchIdsInteract
import vn.toanpv.sample.arena.domain.team.match.reminder.UpdateRemindMatchIdsInteractImpl
import vn.toanpv.sample.arena.domain.team.sync.GetSyncDataStatusInteract
import vn.toanpv.sample.arena.domain.team.sync.GetSyncDataStatusInteractImpl
import vn.toanpv.sample.arena.domain.team.sync.SyncDataInteract
import vn.toanpv.sample.arena.domain.team.sync.SyncDataInteractImpl

val userCaseModule: Module = module {
    single<SyncDataInteract> { SyncDataInteractImpl(get(), get(), get()) }

    single<GetSyncDataStatusInteract> { GetSyncDataStatusInteractImpl(get()) }

    single<GetTeamsInteract> { GetTeamsInteractImpl(get()) }

    single<GetTeamsByNameInteract> { GetTeamsByNameInteractImpl(get()) }

    single<GetMatchesInteract> { GetMatchesInteractImpl(get()) }

    single<GetMatchByTeamInteract> { GetMatchByTeamInteractImpl(get()) }

    single<GetUpcomingMatchesInteract> { GetUpcomingMatchesInteractImpl(get()) }

    single<GetPreviousMatchesInteract> { GetPreviousMatchesInteractImpl(get()) }

    single<GetRemindMatchIdsInteract> { GetRemindMatchIdsInteractImpl(get()) }

    single<UpdateRemindMatchIdsInteract> { UpdateRemindMatchIdsInteractImpl(get()) }

    single<ToggleRemindMatchIdsInteract> { ToggleRemindMatchIdsInteractImpl(get(), get()) }
}
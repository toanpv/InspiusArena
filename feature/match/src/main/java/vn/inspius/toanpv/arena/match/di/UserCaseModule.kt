package vn.inspius.toanpv.arena.match.di

import org.koin.core.module.Module
import org.koin.dsl.module
import vn.inspius.toanpv.arena.domain.team.GetTeamsByNameInteract
import vn.inspius.toanpv.arena.domain.team.GetTeamsByNameInteractImpl
import vn.inspius.toanpv.arena.domain.team.GetTeamsInteract
import vn.inspius.toanpv.arena.domain.team.GetTeamsInteractImpl
import vn.inspius.toanpv.arena.domain.team.match.GetMatchByTeamInteract
import vn.inspius.toanpv.arena.domain.team.match.GetMatchByTeamInteractImpl
import vn.inspius.toanpv.arena.domain.team.match.GetMatchesInteract
import vn.inspius.toanpv.arena.domain.team.match.GetMatchesInteractImpl
import vn.inspius.toanpv.arena.domain.team.match.GetPreviousMatchesInteract
import vn.inspius.toanpv.arena.domain.team.match.GetPreviousMatchesInteractImpl
import vn.inspius.toanpv.arena.domain.team.match.GetUpcomingMatchesInteract
import vn.inspius.toanpv.arena.domain.team.match.GetUpcomingMatchesInteractImpl
import vn.inspius.toanpv.arena.domain.team.match.reminder.GetRemindMatchIdsInteract
import vn.inspius.toanpv.arena.domain.team.match.reminder.GetRemindMatchIdsInteractImpl
import vn.inspius.toanpv.arena.domain.team.match.reminder.ToggleRemindMatchIdsInteract
import vn.inspius.toanpv.arena.domain.team.match.reminder.ToggleRemindMatchIdsInteractImpl
import vn.inspius.toanpv.arena.domain.team.match.reminder.UpdateRemindMatchIdsInteract
import vn.inspius.toanpv.arena.domain.team.match.reminder.UpdateRemindMatchIdsInteractImpl
import vn.inspius.toanpv.arena.domain.team.sync.GetSyncDataStatusInteract
import vn.inspius.toanpv.arena.domain.team.sync.GetSyncDataStatusInteractImpl
import vn.inspius.toanpv.arena.domain.team.sync.SyncDataInteract
import vn.inspius.toanpv.arena.domain.team.sync.SyncDataInteractImpl

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
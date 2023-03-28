package vn.inspius.toanpv.arena.match.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import vn.inspius.toanpv.arena.match.ui.match.detail.MatchDetailViewModel
import vn.inspius.toanpv.arena.match.ui.match.list.MatchesViewModel
import vn.inspius.toanpv.arena.match.ui.match.model.MatchPrevious
import vn.inspius.toanpv.arena.match.ui.team.TeamsSelectorViewModel

val viewModelModule = module {
    viewModel { MatchesViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { (idSelected: String?) -> TeamsSelectorViewModel(idSelected, get(), get()) }
    viewModel { (match: MatchPrevious) -> MatchDetailViewModel(match) }
}
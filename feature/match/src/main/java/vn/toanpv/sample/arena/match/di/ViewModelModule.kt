package vn.toanpv.sample.arena.match.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import vn.toanpv.sample.arena.match.ui.match.detail.MatchDetailViewModel
import vn.toanpv.sample.arena.match.ui.match.list.MatchesViewModel
import vn.toanpv.sample.arena.match.ui.match.model.MatchPrevious
import vn.toanpv.sample.arena.match.ui.team.TeamsSelectorViewModel

val viewModelModule = module {
    viewModel { MatchesViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { (idSelected: String?) -> TeamsSelectorViewModel(idSelected, get(), get()) }
    viewModel { (match: MatchPrevious) -> MatchDetailViewModel(match) }
}
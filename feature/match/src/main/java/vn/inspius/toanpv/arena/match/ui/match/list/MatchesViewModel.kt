package vn.inspius.toanpv.arena.match.ui.match.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.inspius.toanpv.arena.core.ui.RetrieveDataState
import vn.inspius.toanpv.arena.core.ui.retrieveData
import vn.inspius.toanpv.arena.domain.team.GetTeamsInteract
import vn.inspius.toanpv.arena.domain.team.match.GetPreviousMatchesInteract
import vn.inspius.toanpv.arena.domain.team.match.GetUpcomingMatchesInteract
import vn.inspius.toanpv.arena.domain.team.match.reminder.GetRemindMatchIdsInteract
import vn.inspius.toanpv.arena.domain.team.match.reminder.ToggleRemindMatchIdsInteract
import vn.inspius.toanpv.arena.domain.team.sync.GetSyncDataStatusInteract
import vn.inspius.toanpv.arena.entity.Team
import vn.inspius.toanpv.arena.match.ui.BaseViewModel
import vn.inspius.toanpv.arena.match.ui.match.model.FilteredData
import vn.inspius.toanpv.arena.match.ui.match.model.MatchPrevious
import vn.inspius.toanpv.arena.match.ui.match.model.MatchUpcoming
import vn.inspius.toanpv.arena.match.ui.match.model.toPrevious
import vn.inspius.toanpv.arena.match.ui.match.model.toUpcoming
import vn.inspius.toanpv.arena.match.ui.team.model.TeamItem
import vn.inspius.toanpv.arena.match.ui.team.model.toItemList

class MatchesViewModel(
    private val teamInteract: GetTeamsInteract,
    private val upcomingMatchesInteract: GetUpcomingMatchesInteract,
    private val previousMatchesInteract: GetPreviousMatchesInteract,
    private val getRemindMatchIdsInteract: GetRemindMatchIdsInteract,
    private val toggleRemindMatchIdsInteract: ToggleRemindMatchIdsInteract,
    private val getSyncDataStatusInteract: GetSyncDataStatusInteract
) : BaseViewModel() {
    var filteredData: FilteredData = FilteredData(teamId = "")
    private val filteredDataLd: MutableLiveData<FilteredData> = MutableLiveData()
    private val _selectedTeam: LiveData<TeamItem?> = filteredDataLd.switchMap {
        liveData { emit(_teamMap.value?.get(it.teamId)?.toItemList()) }
    }
    val selectedTeam
        get() = _selectedTeam

    /**
     * Store all teams
     */
    private var _teams: LiveData<List<Team>> = filteredDataLd.switchMap {
        liveData(Dispatchers.IO + handler) {
            emit(teamInteract.execute())
        }
    }
    val teams
        get() = _teams

    private val _teamMap: LiveData<Map<String, Team>> = _teams.switchMap { teams ->
        liveData(Dispatchers.IO + handler) {
            emit(teams.associateBy { it.id })
        }
    }

    private var _matchesUpcoming: MediatorLiveData<RetrieveDataState<List<MatchUpcoming>>> =
        MediatorLiveData<RetrieveDataState<List<MatchUpcoming>>>().apply {
            addSource(_teamMap) { teamMap ->
                viewModelScope.launch {
                    retrieveData(Dispatchers.IO + handler) {
                        upcomingMatchesInteract.execute(
                            if (filteredData.teamId.isNullOrEmpty()) null else GetUpcomingMatchesInteract.Param(
                                filteredData.teamId
                            )
                        ).map { match ->
                            match.toUpcoming(teamMap, _remindedIds)
                        }
                    }
                }
            }
        }

    private var _matchesPrevious: LiveData<RetrieveDataState<List<MatchPrevious>>> =
        _teamMap.switchMap { teamMap ->
            retrieveData(Dispatchers.IO + handler) {
                previousMatchesInteract.execute(
                    if (filteredData.teamId.isNullOrEmpty()) null
                    else GetPreviousMatchesInteract.Param(
                        filteredData.teamId
                    )
                ).map { it.toPrevious(teamMap) }
            }
        }
    val matchesPrevious: LiveData<RetrieveDataState<List<MatchPrevious>>>
        get() = _matchesPrevious

    private val _remindedMatchIds = MutableStateFlow(emptySet<String>())
    private var _remindedIds: Set<String> = mutableSetOf()

    private var _matchesUpcomingFlow: SharedFlow<RetrieveDataState<List<MatchUpcoming>>> =
        combine(_teamMap.asFlow(), _remindedMatchIds) { teamMap, matchIds ->
            RetrieveDataState.Success(upcomingMatchesInteract.execute(
                if (filteredData.teamId.isNullOrEmpty()) null else GetUpcomingMatchesInteract.Param(
                    filteredData.teamId
                )
            ).map { match ->
                match.toUpcoming(teamMap, matchIds)
            })
        }.shareIn(viewModelScope, SharingStarted.Eagerly, 0)
    val matchesUpcomingFlow
        get() = _matchesUpcomingFlow

    private var _dataSyncingFlow = MutableSharedFlow<Boolean>()
    val dataSyncingFlow = _dataSyncingFlow.asSharedFlow()

    init {
        filter()
        viewModelScope.launch(Dispatchers.IO + handler) {
            getRemindMatchIdsInteract.execute().collect { matchIds ->
                _remindedIds = matchIds
                _remindedMatchIds.emit(_remindedIds)
                withContext(Dispatchers.Main) {
                    _matchesUpcoming.value = _matchesUpcoming.value
                }
            }
        }
        viewModelScope.launch(Dispatchers.IO + handler) {
            getSyncDataStatusInteract.execute().collect { syncing ->
                _dataSyncingFlow.emit(syncing)
            }
        }
    }

    fun refreshData() {
        filteredDataLd.postValue(filteredData)
    }

    private fun filter(filteredData: FilteredData = FilteredData()) {
        if (filteredData != this.filteredData) {
            this.filteredData = filteredData
            filteredDataLd.postValue(filteredData)
        }
    }

    fun filterTeam(teamId: String?) {
        filter(filteredData.copy(teamId = teamId))
    }

    suspend fun toggleReminder(matchUpcoming: MatchUpcoming): Boolean {
        return toggleRemindMatchIdsInteract.execute(ToggleRemindMatchIdsInteract.Param(matchUpcoming.id))
    }
}
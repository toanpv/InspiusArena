package vn.toanpv.sample.arena.match.ui.team;

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.toanpv.sample.arena.core.ui.RetrieveDataState
import vn.toanpv.sample.arena.domain.team.GetTeamsByNameInteract
import vn.toanpv.sample.arena.domain.team.GetTeamsInteract
import vn.toanpv.sample.arena.match.ui.BaseViewModel
import vn.toanpv.sample.arena.match.ui.team.model.TeamItem
import vn.toanpv.sample.arena.match.ui.team.model.toItemList

class TeamsSelectorViewModel(
    private val idSelected: String? = null,
    private val getTeamsInteract: GetTeamsInteract,
    private val getTeamsByNameInteract: GetTeamsByNameInteract,
) : BaseViewModel() {

    private val _query = MutableLiveData("")

    private var _items: MutableList<TeamItem> = mutableListOf()
    private var _searchItems: MutableList<TeamItem> = mutableListOf()
    private val _loadItems: MediatorLiveData<RetrieveDataState<MutableList<TeamItem>>>
        get() = MediatorLiveData<RetrieveDataState<MutableList<TeamItem>>>().apply {
            loadItems()
        }
    val items: LiveData<RetrieveDataState<MutableList<TeamItem>>> get() = _loadItems

    private val _loadSearchItems: MediatorLiveData<RetrieveDataState<MutableList<TeamItem>>>
        get() = MediatorLiveData<RetrieveDataState<MutableList<TeamItem>>>().apply {
            addSource(_query) { query ->
                if (query.isNullOrEmpty()) {
                    _searchItems.clear()
                    _loadSearchItems.postValue(RetrieveDataState.Success(mutableListOf()))
                } else {
                    loadItems(query)
                }
            }
        }
    val searchItem
        get() = _loadSearchItems

    fun reload() {
        viewModelScope.launch(Dispatchers.IO + handler) {
            if (_items.isNotEmpty()) {
                _items.clear()
                _loadItems.postValue(RetrieveDataState.Success(mutableListOf()))
            }
            _query.postValue(_query.value)
        }
    }

    fun searchItems(query: String) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            if (_searchItems.isNotEmpty()) {
                _searchItems.clear()
                _loadItems.postValue(RetrieveDataState.Success(mutableListOf()))
            }
            _query.postValue(query)
        }
    }

    private fun MediatorLiveData<RetrieveDataState<MutableList<TeamItem>>>.loadItems(
        query: String? = null
    ) {
        viewModelScope.launch {
            postValue(RetrieveDataState.Start)
            withContext(Dispatchers.IO + handler) {
                try {
                    val items = (if (query.isNullOrEmpty())
                        getTeamsInteract.execute()
                    else getTeamsByNameInteract.execute(GetTeamsByNameInteract.Param(query, 4)))
                        .map { it.toItemList(idSelected) }.toMutableList()
                    postValue(RetrieveDataState.Success(items))
                } catch (ex: Exception) {
                    handler.handleException(coroutineContext, ex)
                    ex.printStackTrace()
                    postValue(RetrieveDataState.Error(ex))
                }
            }
        }
    }
}
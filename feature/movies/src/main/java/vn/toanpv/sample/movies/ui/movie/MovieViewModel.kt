package vn.toanpv.sample.movies.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.toanpv.sample.arena.core.ui.RetrieveDataState
import vn.toanpv.sample.arena.core.ui.fragment.BaseViewModel
import vn.toanpv.sample.movies.domain.SearchMoviesInteract
import vn.toanpv.sample.movies.entity.MovieType
import vn.toanpv.sample.movies.ui.movie.model.MovieItem
import vn.toanpv.sample.movies.ui.movie.model.toItemList

class MovieViewModel(
    private val searchMoviesInteract: SearchMoviesInteract
) : BaseViewModel() {

    private var _page: Int = 1
    private val _query = MutableLiveData("")
    val query: LiveData<String>
        get() = _query

    private var _items: MutableList<MovieItem> = mutableListOf()
    private var _searchItems: MediatorLiveData<RetrieveDataState<MutableList<MovieItem>>> =
        MediatorLiveData<RetrieveDataState<MutableList<MovieItem>>>().apply {
            addSource(_query) { query ->
                loadItems(query, _page, _items)
            }
        }
    val items: LiveData<RetrieveDataState<MutableList<MovieItem>>> get() = _searchItems

    private fun onSearch(query: String) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            _query.postValue(query)
        }
    }

    fun search(query: String) {
        _page = 1
        _items.clear()
        _searchItems.postValue(RetrieveDataState.Success(mutableListOf()))
        onSearch(query)
    }

    fun searchMore() {
        _query.value?.let {
            _page += 1
            onSearch(it)
        }
    }

    private val _queryHint = MutableLiveData("")
    private var _hintItems: MutableList<MovieItem> = mutableListOf()
    private val _loadHintItems: MediatorLiveData<RetrieveDataState<MutableList<MovieItem>>> =
        MediatorLiveData<RetrieveDataState<MutableList<MovieItem>>>().apply {
            addSource(_queryHint) { query ->
                if (query.isNullOrEmpty()) {
                    _hintItems.clear()
                    postValue(RetrieveDataState.Success(mutableListOf()))
                } else loadItems(query, 1)
            }
        }
    val hintItems
        get() = _loadHintItems

    init {
        _query.postValue("Marvel")
    }

    fun hintItems(query: String) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            _queryHint.postValue(query)
        }
    }

    private fun MediatorLiveData<RetrieveDataState<MutableList<MovieItem>>>.loadItems(
        query: String? = null, page: Int = 1, currentItems: MutableList<MovieItem>? = null
    ) {
        viewModelScope.launch {
            postValue(RetrieveDataState.Start)
            withContext(Dispatchers.IO + handler) {
                try {
                    val items = (if (query.isNullOrEmpty()) mutableListOf()
                    else searchMoviesInteract.execute(
                        SearchMoviesInteract.Param(
                            query, MovieType.MOVIE, page
                        )
                    ))?.map { it.toItemList() }?.toMutableList() ?: mutableListOf()
                    if (currentItems != null) {
                        currentItems.addAll(items)
                        postValue(RetrieveDataState.Success(currentItems))
                    } else postValue(RetrieveDataState.Success(items))
                } catch (ex: Exception) {
                    handler.handleException(coroutineContext, ex)
                    ex.printStackTrace()
                    postValue(RetrieveDataState.Error(ex))
                }
            }
        }
    }
}
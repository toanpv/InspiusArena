package vn.toanpv.sample.movies.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vn.toanpv.sample.arena.core.ui.RetrieveDataState
import vn.toanpv.sample.arena.core.ui.fragment.BaseViewModel
import vn.toanpv.sample.movies.domain.GetMovieDetailsByIdInteract
import vn.toanpv.sample.movies.domain.GetMovieDetailsInteract
import vn.toanpv.sample.movies.ui.movie.model.MovieItem
import vn.toanpv.sample.movies.ui.movie.model.toItemList

class MovieDetailViewModel(
    private val getMovieDetailsInteract: GetMovieDetailsInteract,
    private val getMovieDetailsByIdInteract: GetMovieDetailsByIdInteract,
    private val initMovie: MovieItem
) :
    BaseViewModel() {
    private var _movie: MutableLiveData<RetrieveDataState<MovieItem>> = MutableLiveData()
    val movieItem:LiveData<RetrieveDataState<MovieItem>>
        get() = _movie

    init {
        viewModelScope.launch(Dispatchers.IO + handler) {
            try {
                _movie.postValue(RetrieveDataState.Start)
                val movie = if (initMovie.imdbID.isNotEmpty()) {
                    getMovieDetailsByIdInteract.execute(
                        GetMovieDetailsByIdInteract.Params(
                            initMovie.imdbID
                        )
                    )
                } else
                    getMovieDetailsInteract.execute(GetMovieDetailsInteract.Params(initMovie.title))
                _movie.postValue(RetrieveDataState.Success(movie?.toItemList() ?: initMovie))
            } catch (ex: Exception) {
                ex.printStackTrace()
                _movie.postValue(RetrieveDataState.Error(ex))
            }
        }
    }

}
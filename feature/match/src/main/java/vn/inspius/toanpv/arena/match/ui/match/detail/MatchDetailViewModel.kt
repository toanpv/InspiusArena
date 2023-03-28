package vn.inspius.toanpv.arena.match.ui.match.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import vn.inspius.toanpv.arena.match.ui.BaseViewModel
import vn.inspius.toanpv.arena.match.ui.match.model.MatchPrevious

class MatchDetailViewModel(private var matchUI: MatchPrevious) : BaseViewModel() {
    private var _match: MutableLiveData<MatchPrevious> = MutableLiveData(matchUI)
    val match: LiveData<MatchPrevious>
        get() = _match

    fun setMatch(matchUI: MatchPrevious) {
        _match.postValue(matchUI)
    }

}
package vn.inspius.toanpv.arena.match.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

open class BaseViewModel : ViewModel() {
    val handler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }
}
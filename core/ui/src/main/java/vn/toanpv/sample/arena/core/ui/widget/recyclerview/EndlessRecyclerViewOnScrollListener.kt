package vn.toanpv.sample.arena.core.ui.widget.recyclerview

import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessRecyclerViewOnScrollListener(
    threshold: Int = THRESHOLD
) : RecyclerView.OnScrollListener() {
    var isLoading = false
    private var previousTotal: Int = 0
    private var visibleItem: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    private var numberThreshold: Int = when {
        threshold >= 1 -> threshold
        else -> THRESHOLD
    }

    private companion object {
        const val THRESHOLD = 5
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        visibleItemCount = recyclerView.childCount
        totalItemCount = recyclerView.layoutManager?.itemCount ?: 0
        when (val layoutManager = recyclerView.layoutManager) {
            is GridLayoutManager -> {
                visibleItem = layoutManager.findFirstVisibleItemPosition()
                numberThreshold *= layoutManager.spanCount
            }
            is LinearLayoutManager -> {
                visibleItem = layoutManager.findFirstVisibleItemPosition()
            }
            else -> {
                Log.e("EndlessScrollListener", "Unsupported layout manager")
            }
        }

        if (isLoading) {
            stateLoading()
        }

        if (isLoading.not() && totalItemCount - visibleItemCount <= visibleItem + numberThreshold) {
            onLoadMore()
            isLoading = true
        }
    }


    private fun stateLoading() {
        if (totalItemCount != previousTotal) {
            isLoading = false
            previousTotal = totalItemCount
        }
    }

    fun resetOnLoadMore() {
        visibleItem = 0
        visibleItemCount = 0
        totalItemCount = 0
        previousTotal = 0
        isLoading = true
    }

    abstract fun onLoadMore()
}
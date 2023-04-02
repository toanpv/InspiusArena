package vn.toanpv.sample.movies.ui.movie.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.toanpv.sample.arena.core.ui.widget.recyclerview.BaseRecyclerViewAdapter
import vn.toanpv.sample.movies.databinding.ItemMovieSearch1Binding
import vn.toanpv.sample.movies.ui.movie.model.MovieItem

class MovieAdapter(private val onItemClick: (MovieItem, View, View) -> Unit) :
    BaseRecyclerViewAdapter<MovieItem, MovieSearchItemViewHolder>() {
    override fun areItemsSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
        return oldItem.imdbID == newItem.imdbID
    }

    override fun areContentsSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
        return oldItem.bookmarked == newItem.bookmarked || oldItem.poster == newItem.poster || oldItem.title == newItem.title
    }

    override fun getPayload(oldItem: MovieItem, newItem: MovieItem): Bundle {
        return Bundle().apply {
            putBoolean(PAYLOAD_BOOKMARK, oldItem.bookmarked != newItem.bookmarked)
            putBoolean(PAYLOAD_POSTER, oldItem.poster != newItem.poster)
            putBoolean(PAYLOAD_TITLE, oldItem.title != newItem.title)
        }
    }

    override fun createHolder(parent: ViewGroup, viewType: Int): MovieSearchItemViewHolder {
        return MovieSearchItemViewHolder(
            ItemMovieSearch1Binding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onItemClick
        )
    }

    companion object {
        const val PAYLOAD_BOOKMARK = "payload_check"
        const val PAYLOAD_TITLE = "payload_name"
        const val PAYLOAD_POSTER = "payload_logo"
    }
}
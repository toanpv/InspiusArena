package vn.toanpv.sample.movies.ui.movie.adapter.search

import android.view.LayoutInflater
import android.view.ViewGroup
import vn.toanpv.sample.movies.ui.movie.model.MovieItem
import vn.toanpv.sample.arena.core.ui.widget.recyclerview.BaseRecyclerViewAdapter
import vn.toanpv.sample.movies.databinding.ItemMovieSearchHintBinding

class MovieHintAdapter(private val onItemClick: (MovieItem) -> Unit) :
    BaseRecyclerViewAdapter<MovieItem, MovieSearchHintViewHolder>() {
    override fun areItemsSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
        return oldItem.imdbID == newItem.imdbID
    }

    override fun areContentsSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
        return oldItem.title == newItem.title
    }

    override fun createHolder(parent: ViewGroup, viewType: Int): MovieSearchHintViewHolder {
        return MovieSearchHintViewHolder(
            ItemMovieSearchHintBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onItemClick
        )
    }
}
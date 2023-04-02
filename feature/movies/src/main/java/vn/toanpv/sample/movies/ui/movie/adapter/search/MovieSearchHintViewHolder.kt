package vn.toanpv.sample.movies.ui.movie.adapter.search

import vn.toanpv.sample.movies.ui.movie.model.MovieItem
import vn.toanpv.sample.arena.core.ui.widget.recyclerview.BaseRecyclerViewAdapter
import vn.toanpv.sample.movies.databinding.ItemMovieSearchHintBinding

class MovieSearchHintViewHolder(
    private val vb: ItemMovieSearchHintBinding,
    private val onItemClick: (MovieItem) -> Unit
) : BaseRecyclerViewAdapter.BaseViewHolder<MovieItem>(vb) {
    init {
        vb.root.setOnClickListener {
            item?.let { item ->
                onItemClick.invoke(item)
            }
        }
    }

    override fun bind(item: MovieItem?) {
        super.bind(item)
        bindTitle(item)
    }

    private fun bindTitle(item: MovieItem?) {
        vb.tvName.text = item?.title ?: ""
    }
}
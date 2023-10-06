package vn.toanpv.sample.movies.ui.movie.adapter

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import coil.load
import vn.toanpv.sample.arena.core.ui.widget.recyclerview.BaseRecyclerViewAdapter
import vn.toanpv.sample.movies.R
import vn.toanpv.sample.movies.databinding.ItemMovieSearch1Binding
import vn.toanpv.sample.movies.ui.movie.model.MovieItem

class MovieSearchItemViewHolder(
    private val vb: ItemMovieSearch1Binding,
    private val onItemClick: (MovieItem, View, View) -> Unit
) : BaseRecyclerViewAdapter.BaseViewHolder<MovieItem>(vb) {

    init {
        vb.root.setOnClickListener {
            item?.let { item ->
                ViewCompat.setTransitionName(vb.tvTitle, "movie_title")
                ViewCompat.setTransitionName(vb.ivPoster, "movie_image")
                onItemClick.invoke(item, vb.tvTitle, vb.ivPoster)
            }
        }
    }

    override fun bind(item: MovieItem?) {
        super.bind(item)
        bindTitle(item)
        bindPoster(item)
        bindBookmark(item)
    }

    override fun bind(item: MovieItem?, payload: Bundle) {
        super.bind(item, payload)
        if (payload.getBoolean(MovieAdapter.PAYLOAD_BOOKMARK, false)) bindBookmark(item)
        if (payload.getBoolean(MovieAdapter.PAYLOAD_TITLE, false)) bindTitle(item)
        if (payload.getBoolean(MovieAdapter.PAYLOAD_TITLE, false)) bindPoster(item)
    }

    private fun bindTitle(item: MovieItem?) {
        vb.tvTitle.text = item?.title ?: ""
    }

    private fun bindPoster(item: MovieItem?) {
        vb.ivPoster.load(item?.poster) {
            memoryCacheKey("movie_${item!!.imdbID}")
            crossfade(true)
            allowHardware(false)
            error(R.drawable.ic_movie_error)
        }
    }

    private fun bindBookmark(item: MovieItem?) {
        vb.ivBookmarked.isVisible = item?.bookmarked ?: false
    }
}
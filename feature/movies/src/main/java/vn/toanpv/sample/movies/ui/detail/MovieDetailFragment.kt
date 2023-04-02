package vn.toanpv.sample.movies.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import coil.load
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import vn.toanpv.sample.arena.core.ui.RetrieveDataState
import vn.toanpv.sample.arena.core.ui.fragment.BaseViewBindingFragment
import vn.toanpv.sample.arena.core.ui.widget.snackbar
import vn.toanpv.sample.arena.core.ui.widget.topbar.syncTitle
import vn.toanpv.sample.movies.R
import vn.toanpv.sample.movies.databinding.FragmentMovieDetailBinding
import vn.toanpv.sample.movies.databinding.ItemMovieGenreBinding
import vn.toanpv.sample.movies.ui.movie.model.MovieItem


class MovieDetailFragment :
    BaseViewBindingFragment<FragmentMovieDetailBinding>(FragmentMovieDetailBinding::inflate) {

    private val navArgs by navArgs<MovieDetailFragmentArgs>()
    private val vm by viewModel<MovieDetailViewModel> { parametersOf(navArgs.movie) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val transition = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        initView()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    private fun initView() {
        val movie = navArgs.movie
        with(vb) {
            ivPoster.load(movie.poster) {
                crossfade(true)
                placeholderMemoryCacheKey("movie_${movie.imdbID}")
                allowHardware(false)
                error(R.drawable.ic_movie_error)
            }
            tvTitle.text = movie.title
            appBar.syncTitle(movie.title, tvTitle, toolbar, toolbarLayout)
            toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
            cImdbRate.text = getString(R.string.movie_imdb_rate, "")
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigateUp()
        }
    }

    private fun initData() {
        with(vm) {
            movieItem.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is RetrieveDataState.Start -> {}
                    is RetrieveDataState.Error -> {
                        state.throwable.message?.let { vb.snackbar(it) }
                    }
                    is RetrieveDataState.Success -> {
                        initDetail(state.data)
                    }
                }
            }
        }
    }

    private fun initDetail(movie: MovieItem) {
        with(vb) {
            cgGenre.apply {
                removeAllViews()
                movie.genre?.split(",")?.forEach { genre ->
                    val itemChip = ItemMovieGenreBinding.inflate(layoutInflater, cgGenre, false)
                    itemChip.genre.text = genre
                    cgGenre.addView(itemChip.root)
                }
            }
            tvPlot.text = movie.plot
            cImdbRate.text = getString(R.string.movie_imdb_rate, movie.imdbRating)
            tvImdbReviewCount.text = getString(R.string.movie_imdb_votes, movie.imdbVotes)
        }
    }
}
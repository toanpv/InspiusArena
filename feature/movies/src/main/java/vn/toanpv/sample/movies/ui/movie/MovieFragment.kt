package vn.toanpv.sample.movies.ui.movie

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.toanpv.sample.arena.core.ui.R
import vn.toanpv.sample.arena.core.ui.RetrieveDataState
import vn.toanpv.sample.arena.core.ui.addSubmittedText
import vn.toanpv.sample.arena.core.ui.fragment.BaseViewBindingFragment
import vn.toanpv.sample.arena.core.ui.widget.recyclerview.EndlessRecyclerViewOnScrollListener
import vn.toanpv.sample.arena.core.ui.widget.recyclerview.EqualSpacingItemDecoration
import vn.toanpv.sample.movies.databinding.FragmentMovieSearchBinding
import vn.toanpv.sample.movies.ui.movie.adapter.MovieAdapter
import vn.toanpv.sample.movies.ui.movie.adapter.search.MovieHintAdapter
import vn.toanpv.sample.movies.ui.movie.model.MovieItem

class MovieFragment :
    BaseViewBindingFragment<FragmentMovieSearchBinding>(FragmentMovieSearchBinding::inflate) {

    private lateinit var onLoadMore: EndlessRecyclerViewOnScrollListener
    private val vm by viewModel<MovieViewModel>()
    private var _adapter = MovieAdapter { item, title, image -> gotoDetail(item, title, image) }
    private var _adapterSearch = MovieHintAdapter { submitSearch(it.title) }

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
        with(vb) {
            searchBar.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    vn.toanpv.sample.movies.R.id.menu_to_match -> {
                        NavDeepLinkRequest.Builder.fromUri(
                            Uri.parse(
                                getString(R.string.dl_to_match)
                            )
                        )
                            .build().let {
                                findNavController().navigate(
                                    it,
                                    NavOptions.Builder().setLaunchSingleTop(true).build()
                                )
                            }
                    }
                }
                true
            }
            svInput.apply {
                editText.apply {
                    setOnEditorActionListener { _, _, _ ->
                        submitSearch(text.toString().trim())
                        false
                    }
                    addSubmittedText(delayMillis = 1000) { hint(text.toString().trim()) }
                }
            }
            val spacing =
                requireContext().resources.getDimensionPixelSize(R.dimen.content_padding_normal)
            rvMovie.apply {
                layoutManager = GridLayoutManager(context, 2)
                addItemDecoration(
                    EqualSpacingItemDecoration(
                        spacing, displayMode = EqualSpacingItemDecoration.GRID
                    )
                )
                adapter = _adapter
                onLoadMore = object : EndlessRecyclerViewOnScrollListener() {
                    override fun onLoadMore() {
                        vm.searchMore()
                    }
                }
                addOnScrollListener(onLoadMore)
            }
            rvMovieSearch.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                addItemDecoration(
                    EqualSpacingItemDecoration(
                        displayMode = EqualSpacingItemDecoration.VERTICAL
                    )
                )
                adapter = _adapterSearch
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (vb.svInput.isShowing) {
                vb.svInput.apply {
                    clearText()
                    hide()
                }
            } else if (!findNavController().navigateUp())
                requireActivity().finish()
        }
    }

    private fun initData() {
        with(vm) {
            query.observe(viewLifecycleOwner) { title ->
                vb.searchBar.text = title
            }
            items.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is RetrieveDataState.Start -> {
                        vb.loadMoreIndicator.isVisible = true
                        vb.lFoundNothing.root.isVisible = false
                    }
                    is RetrieveDataState.Error -> {
                        vb.loadMoreIndicator.isVisible = false
                        vb.lFoundNothing.root.isVisible = _adapter.itemCount == 0
                    }
                    is RetrieveDataState.Success -> {
                        lifecycleScope.launch(Dispatchers.IO) {
                            _adapter.setData(state.data.toMutableList()) {
                                vb.lFoundNothing.root.isVisible = _adapter.itemCount == 0
                            }
                        }
                        vb.loadMoreIndicator.isVisible = false
                    }
                }
            }
            hintItems.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is RetrieveDataState.Start -> {
                    }
                    is RetrieveDataState.Error -> {
                        lifecycleScope.launch(Dispatchers.IO) {
                            _adapterSearch.setData(mutableListOf()) {}
                        }
                    }
                    is RetrieveDataState.Success -> {
                        lifecycleScope.launch(Dispatchers.IO) {
                            _adapterSearch.setData(state.data.toMutableList()) {
                                vb.rvMovieSearch.smoothScrollToPosition(0)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun gotoDetail(item: MovieItem, title: View, image: View) {
        val extras = FragmentNavigatorExtras(
            title to "movie_title",
            image to "movie_image"
        )
        val acToDetail = MovieFragmentDirections.acToDetail(item)
        findNavController().navigate(
            acToDetail.actionId, acToDetail.arguments, NavOptions.Builder().setPopExitAnim(
                vn.toanpv.sample.movies.R.anim.slide_out
            ).setPopEnterAnim(vn.toanpv.sample.movies.R.anim.fade_in)
                .build(), extras
        )
    }

    private fun hint(text: String) {
        requireActivity().runOnUiThread {
            vm.hintItems(text)
        }
    }

    private fun submitSearch(hint: String) {
        onLoadMore.resetOnLoadMore()
        vb.svInput.hide()
        vm.search(hint)
    }
}
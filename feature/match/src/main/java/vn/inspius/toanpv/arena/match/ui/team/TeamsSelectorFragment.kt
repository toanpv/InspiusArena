package vn.inspius.toanpv.arena.match.ui.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import vn.inspius.toanpv.arena.core.ui.R
import vn.inspius.toanpv.arena.core.ui.RetrieveDataState
import vn.inspius.toanpv.arena.core.ui.addSubmittedText
import vn.inspius.toanpv.arena.core.ui.widget.recyclerview.EqualSpacingItemDecoration
import vn.inspius.toanpv.arena.match.databinding.FragmentTeamsSelectorBinding
import vn.inspius.toanpv.arena.match.ui.BaseViewBindingFragment
import vn.inspius.toanpv.arena.match.ui.team.adapter.TeamSelectorAdapter
import vn.inspius.toanpv.arena.match.ui.team.model.TeamItem

class TeamsSelectorFragment :
    BaseViewBindingFragment<FragmentTeamsSelectorBinding>(FragmentTeamsSelectorBinding::inflate) {

    private val navArgs by navArgs<TeamsSelectorFragmentArgs>()
    private val vm by viewModel<TeamsSelectorViewModel> { parametersOf(navArgs.idSelected) }
    private var _adapter = TeamSelectorAdapter { returnResult(it) }
    private var _adapterSearch = TeamSelectorAdapter { returnResult(it) }


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
            svInput.apply {
                editText.setOnEditorActionListener { _, _, _ ->
                    search()
                    hide()
                    false
                }
                editText.addSubmittedText { search() }
            }
            val spacing =
                requireContext().resources.getDimensionPixelSize(R.dimen.content_padding_normal)
            rvTeams.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                addItemDecoration(
                    EqualSpacingItemDecoration(
                        spacing, displayMode = EqualSpacingItemDecoration.VERTICAL
                    )
                )
                adapter = _adapter
                addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
                    lFoundNothing.root.isVisible =
                        adapter?.itemCount == 0 && searchBar.text?.isNotEmpty() == true
                }
            }
            srl.isEnabled = false
            rvTeamsSearch.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                addItemDecoration(
                    EqualSpacingItemDecoration(
                        spacing, displayMode = EqualSpacingItemDecoration.VERTICAL
                    )
                )
                adapter = _adapterSearch
                addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
                    lFoundNothing.root.isVisible =
                        adapter?.itemCount == 0 && searchBar.text?.isNotEmpty() == true
                }
            }
            lSelectAllTeams.apply {
                tvName.setText(vn.inspius.toanpv.arena.match.R.string.teams_item_all)
                ivCheck.isVisible = navArgs.idSelected.isEmpty()
                root.setOnClickListener {
                    returnResult(null)
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (vb.svInput.isShowing) {
                vb.svInput.apply {
                    clearText()
                    hide()
                }
            } else findNavController().navigateUp()
        }
    }

    private fun initData() {
        with(vm) {
            items.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is RetrieveDataState.Start -> {
                        vb.lFoundNothing.root.isGone = true
                    }
                    is RetrieveDataState.Error -> {
                        vb.lFoundNothing.root.isVisible = _adapter.itemCount == 0
                    }
                    is RetrieveDataState.Success -> {
                        lifecycleScope.launch(Dispatchers.IO) {
                            _adapter.setData(state.data.toMutableList()) {
                                vb.lFoundNothing.root.isVisible = _adapter.itemCount == 0
                            }
                        }

                    }
                }
            }
            searchItem.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is RetrieveDataState.Start -> {
                        vb.srl.isRefreshing = true
                    }
                    is RetrieveDataState.Error -> {
                        vb.srl.isRefreshing = false
                    }
                    is RetrieveDataState.Success -> {
                        lifecycleScope.launch(Dispatchers.IO) {
                            _adapterSearch.setData(state.data.toMutableList()) {
                                vb.srl.isRefreshing = false
                                vb.rvTeamsSearch.smoothScrollToPosition(0)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun returnResult(data: TeamItem?) {
        setFragmentResult(
            ATTRIBUTE_ARG, bundleOf(ATTRIBUTE_VALUE to (data?.toItem()?.id ?: ""))
        )
        findNavController().navigateUp()
    }

    private fun search() {
        requireActivity().runOnUiThread {
            vb.lFoundNothing.root.isGone = true
            val query = vb.svInput.text
            vb.searchBar.text = query
            vm.searchItems(query.toString())
        }
    }

    companion object {
        const val ATTRIBUTE_ARG = "attribute_arg"
        const val ATTRIBUTE_VALUE = "attribute_value"
    }
}
package vn.toanpv.sample.arena.match.ui.match.list

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.toanpv.sample.arena.core.ui.RetrieveDataState
import vn.toanpv.sample.arena.core.ui.widget.recyclerview.EqualSpacingItemDecoration
import vn.toanpv.sample.arena.core.ui.widget.scrollWithEfab
import vn.toanpv.sample.arena.core.ui.widget.snackbar
import vn.toanpv.sample.arena.match.R
import vn.toanpv.sample.arena.match.databinding.FragmentMatchesBinding
import vn.toanpv.sample.arena.match.ui.match.list.adapter.MatchesPreviousRecyclerViewAdapter
import vn.toanpv.sample.arena.match.ui.match.list.adapter.MatchesUpcomingRecyclerViewAdapter
import vn.toanpv.sample.arena.match.ui.team.TeamsSelectorFragment
import vn.toanpv.sample.arena.match.worker.cancelRemindedNotification
import vn.toanpv.sample.arena.match.worker.scheduleNotification

class MatchesFragment : Fragment() {

    private var _isNotificationPermissionGranted: Boolean = false
    private var _vb: FragmentMatchesBinding? = null
    private val vb
        get() = _vb!!
    private val vm by viewModel<MatchesViewModel>()

    private lateinit var rvUpcomingAdapter: MatchesUpcomingRecyclerViewAdapter
    private lateinit var rvPreviousAdapter: MatchesPreviousRecyclerViewAdapter
    private lateinit var checkNotificationPermission: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkNotificationPermission = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            _isNotificationPermissionGranted = isGranted
        }
        rvUpcomingAdapter = MatchesUpcomingRecyclerViewAdapter { matchUpcoming ->
            runWithPermission {
                lifecycleScope.launch {
                    val status = withContext(Dispatchers.IO) {
                        vm.toggleReminder(matchUpcoming)
                    }
                    if (status) {
                        requireContext().scheduleNotification(matchUpcoming)
                    } else {
                        requireContext().cancelRemindedNotification(matchUpcoming.id)
                    }
                }
            }
        }
        rvPreviousAdapter = MatchesPreviousRecyclerViewAdapter { match ->
            findNavController().navigate(MatchesFragmentDirections.acMatchDetail(match))
        }
        initResultListener()
        checkPermission()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        if (_vb == null) {
            _vb = FragmentMatchesBinding.inflate(inflater, container, false)
            initView()
        }
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    private fun initResultListener() {
        setFragmentResultListener(TeamsSelectorFragment.ATTRIBUTE_ARG) { _, bundle ->
            @Suppress("DEPRECATION") bundle.getString(TeamsSelectorFragment.ATTRIBUTE_VALUE, null)
                .let {
                    vm.filterTeam(it)
                }
        }
    }

    private fun runWithPermission(block: () -> Unit) {
        if (_isNotificationPermissionGranted) block.invoke()
        else {
            checkPermission()
        }
    }

    private fun checkPermission() {
        _isNotificationPermissionGranted =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (checkSelfPermission(
                        requireContext(), Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    true
                } else {
                    checkNotificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
                    false
                }
            } else {
                true
            }
    }


    private fun initView() {
        val spaceGap =
            requireContext().resources.getDimensionPixelSize(vn.toanpv.sample.arena.core.ui.R.dimen.content_padding_normal)
        with(vb) {
            container.scrollWithEfab(fab)
            toolbar.apply {
                setTitle(R.string.matches_feature_title)
            }
            fab.setOnClickListener {
                findNavController().navigate(
                    MatchesFragmentDirections.acFilterTeam(
                        vm.filteredData.teamId ?: ""
                    )
                )
            }
            rvUpcomingMatches.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(
                    EqualSpacingItemDecoration(
                        spaceGap, displayMode = EqualSpacingItemDecoration.HORIZONTAL
                    )
                )
                adapter = rvUpcomingAdapter
            }
            rvPreviousMatches.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                addItemDecoration(
                    EqualSpacingItemDecoration(
                        spaceGap, displayMode = EqualSpacingItemDecoration.VERTICAL
                    )
                )
                adapter = rvPreviousAdapter
            }
            srl.setOnRefreshListener {
                vm.refreshData()
            }
        }
    }

    private fun initData() {
        with(vm) {
            //Show filter only team loaded
            teams.observe(viewLifecycleOwner) { teams ->
                vb.fab.isGone = teams.isEmpty()
            }
            matchesPrevious.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is RetrieveDataState.Start -> {
                        vb.srl.isRefreshing = true
                        vb.tvEmpty.isGone = true
                    }
                    is RetrieveDataState.Success -> {
                        lifecycleScope.launch(Dispatchers.IO) {
                            rvPreviousAdapter.setData(state.data) {
                                setDataState(
                                    rvPreviousAdapter.itemCount > 0
                                )
                                vb.rvPreviousMatches.smoothScrollToPosition(0)
                            }
                        }
                        vb.srl.isRefreshing = false
                    }
                    is RetrieveDataState.Error -> {
                        vb.srl.isRefreshing = false
                        state.throwable.let {
                            it.printStackTrace()
                            it.localizedMessage?.let { message -> vb.snackbar(message) }
                        }
                    }
                }
            }
            selectedTeam.observe(viewLifecycleOwner) {
                vb.toolbar.subtitle = it?.name ?: ""
            }
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    matchesUpcomingFlow.collect { state ->
                        when (state) {
                            is RetrieveDataState.Start -> {
                            }
                            is RetrieveDataState.Success -> {
                                lifecycleScope.launch(Dispatchers.Main) {
                                    val oldCount = rvUpcomingAdapter.itemCount
                                    rvUpcomingAdapter.setData(state.data) {
                                        setDataState(
                                            rvUpcomingAdapter.itemCount > 0
                                        )
                                        //"try" to make refresh list feeling
                                        if (oldCount != rvUpcomingAdapter.itemCount)
                                            vb.rvUpcomingMatches.smoothScrollToPosition(0)
                                        vb.container.smoothScrollTo(0, 0)
                                    }
                                }
                            }
                            is RetrieveDataState.Error -> {
                                state.throwable.let {
                                    it.printStackTrace()
                                    it.localizedMessage?.let { message -> vb.snackbar(message) }
                                }
                            }
                        }
                    }
                }
            }

            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    dataSyncingFlow.collect { syncing ->
                        if (syncing) {
                            vb.srl.isRefreshing = true
                        } else
                            refreshData()
                    }
                }
            }
        }
    }

    private fun setDataState(hasData: Boolean) {
        vb.rvUpcomingMatches.isVisible = hasData
        vb.tvEmpty.isVisible = !hasData
    }

    override fun onDestroy() {
        _vb = null
        super.onDestroy()
    }
}
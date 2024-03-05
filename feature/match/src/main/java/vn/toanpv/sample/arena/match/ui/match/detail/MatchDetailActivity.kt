package vn.toanpv.sample.arena.match.ui.match.detail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.IntentCompat
import androidx.core.view.MenuProvider
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.navArgs
import com.danikula.videocache.HttpProxyCacheServer
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.inject
import vn.toanpv.sample.arena.core.ui.widget.loadOrClear
import vn.toanpv.sample.arena.match.R
import vn.toanpv.sample.arena.match.databinding.ActivityMatchDetailBinding
import vn.toanpv.sample.arena.match.ui.match.model.MatchPrevious

class MatchDetailActivity : VideoViewPiPActivity() {
    private val navArgs by navArgs<MatchDetailActivityArgs>()
    private val vm by viewModel<MatchDetailViewModel> { parametersOf(navArgs.match) }
    private lateinit var vb: ActivityMatchDetailBinding
    private val httpCache: HttpProxyCacheServer by inject(HttpProxyCacheServer::class.java)

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let { nonNullIntent ->
            IntentCompat.getParcelableExtra(nonNullIntent, "match", MatchPrevious::class.java)
                ?.let { vm.setMatch(it) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityMatchDetailBinding.inflate(layoutInflater)
        setContentView(vb.root)
        setSupportActionBar(vb.toolbar)
        vb.toolbar.apply {
            setNavigationOnClickListener {
                finish()
            }
        }
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    menuInflater.inflate(R.menu.match_detail_top, menu)
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return if (menuItem.itemId == R.id.menu_pip) {
                    startFloatWindow()
                    true
                } else false
            }
        })
        initPlayer(vb.player)
        initData()
    }

    private fun initData() {
        vm.match.observe(this) { match ->
            with(vb) {
                player.apply {
                    release()
                    setUrl(httpCache.getProxyUrl(match.highlights))
                    start()
                }
                bindDetail(vb, match)
            }
        }
    }

    private fun bindDetail(vb: ActivityMatchDetailBinding, match: MatchPrevious?) {
        with(vb) {
            if (match != null) {
                val constraintLayout = detail
                when (match.winnerId) {
                    match.homeId -> {
                        ConstraintSet().apply {
                            clone(constraintLayout)
                            connect(
                                ivWin.id, ConstraintSet.START, ivHomeLogo.id, ConstraintSet.START
                            )
                            connect(
                                ivWin.id, ConstraintSet.TOP, ivHomeLogo.id, ConstraintSet.TOP
                            )
                            applyTo(constraintLayout)
                        }
                    }

                    match.awayId -> {
                        ConstraintSet().apply {
                            clone(constraintLayout)
                            connect(
                                ivWin.id, ConstraintSet.END, ivAwayLogo.id, ConstraintSet.END
                            )
                            connect(
                                ivWin.id, ConstraintSet.TOP, ivAwayLogo.id, ConstraintSet.TOP
                            )
                            applyTo(constraintLayout)
                        }
                    }

                    else -> {
                        ivWin.isVisible = false
                    }
                }
            } else {
                ivWin.isVisible = false
            }
            ivAwayLogo.loadOrClear(match?.awayLogo)
            ivHomeLogo.loadOrClear(match?.homeLogo)
            tvTime.text = match?.time ?: ""
            tvDate.text = match?.date ?: ""
            tvAwayName.text = match?.awayName ?: ""
            tvHomeName.text = match?.homeName ?: ""
            tvDescription.apply {
                if (match != null) {
                    text = match.description
                    isVisible = true
                } else {
                    text = ""
                    isGone = true
                }
            }
        }
    }

    override fun toggleContent(isPip: Boolean) {
        with(vb) {
            detail.isGone = isPip
            toolbar.isGone = isPip
        }
    }
}
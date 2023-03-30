package vn.toanpv.sample.arena.match.ui.match.list.adapter

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import vn.toanpv.sample.arena.core.ui.widget.loadOrClear
import vn.toanpv.sample.arena.core.ui.widget.recyclerview.BaseRecyclerViewAdapter
import vn.toanpv.sample.arena.match.databinding.ItemMatchPreviousBinding
import vn.toanpv.sample.arena.match.ui.match.model.MatchPrevious

class MatchPreviousViewHolder(
    private val vb: ItemMatchPreviousBinding,
    private val onItemClick: (MatchPrevious) -> Unit
) :
    BaseRecyclerViewAdapter.BaseViewHolder<MatchPrevious>(vb) {
    init {
        vb.root.setOnClickListener {
            item?.let { item ->
                onItemClick.invoke(item)
            }
        }
    }

    override fun bind(item: MatchPrevious?) {
        super.bind(item)
        bindHomeName(item)
        bindAwayName(item)
        bindDate(item)
        bindTime(item)
        bindHomeLogo(item)
        bindAwayLogo(item)
        bindWinner(item)
        bindDesc(item)
    }

    override fun bind(item: MatchPrevious?, payload: Bundle) {
        super.bind(item, payload)
        when {
            payload.getBoolean(
                MatchesRecyclerViewAdapter.PAYLOAD_WINNER, false
            ) -> bindWinner(item)
            payload.getBoolean(
                MatchesRecyclerViewAdapter.PAYLOAD_HOME_LOGO, false
            ) -> bindHomeLogo(item)
            payload.getBoolean(
                MatchesRecyclerViewAdapter.PAYLOAD_HOME_NAME, false
            ) -> bindHomeName(item)
            payload.getBoolean(
                MatchesRecyclerViewAdapter.PAYLOAD_AWAY_LOGO, false
            ) -> bindAwayLogo(item)
            payload.getBoolean(
                MatchesRecyclerViewAdapter.PAYLOAD_AWAY_NAME, false
            ) -> bindAwayName(item)
            payload.getBoolean(
                MatchesRecyclerViewAdapter.PAYLOAD_DATE, false
            ) -> bindDate(item)
            payload.getBoolean(
                MatchesRecyclerViewAdapter.PAYLOAD_TIME, false
            ) -> bindTime(item)
            payload.getBoolean(
                MatchesRecyclerViewAdapter.PAYLOAD_DESCRIPTION, false
            ) -> bindDesc(item)
        }
    }

    private fun bindWinner(match: MatchPrevious?) {
        vb.ivWin.isVisible = true
        if (match != null) {
            val constraintLayout = vb.container
            when (match.winnerId) {
                match.homeId -> {
                    ConstraintSet().apply {
                        clone(constraintLayout)
                        connect(
                            vb.ivWin.id, ConstraintSet.START, vb.ivHomeLogo.id, ConstraintSet.START
                        )
                        connect(
                            vb.ivWin.id, ConstraintSet.TOP, vb.ivHomeLogo.id, ConstraintSet.TOP
                        )
                        applyTo(constraintLayout)
                    }
                }
                match.awayId -> {
                    ConstraintSet().apply {
                        clone(constraintLayout)
                        connect(
                            vb.ivWin.id, ConstraintSet.END, vb.ivAwayLogo.id, ConstraintSet.END
                        )
                        connect(
                            vb.ivWin.id, ConstraintSet.TOP, vb.ivAwayLogo.id, ConstraintSet.TOP
                        )
                        applyTo(constraintLayout)
                    }
                }
                else -> {
                    vb.ivWin.isVisible = false
                }
            }
        } else {
            vb.ivWin.isVisible = false
        }
    }

    private fun bindAwayLogo(match: MatchPrevious?) {
        vb.ivAwayLogo.loadOrClear(match?.awayLogo)
    }

    private fun bindHomeLogo(match: MatchPrevious?) {
        vb.ivHomeLogo.loadOrClear(match?.homeLogo)
    }

    private fun bindTime(match: MatchPrevious?) {
        vb.tvTime.text = match?.time ?: ""
    }

    private fun bindDate(match: MatchPrevious?) {
        vb.tvDate.text = match?.date ?: ""
    }

    private fun bindAwayName(match: MatchPrevious?) {
        vb.tvAwayName.text = match?.awayName ?: ""
    }

    private fun bindHomeName(match: MatchPrevious?) {
        vb.tvHomeName.text = match?.homeName ?: ""
    }

    private fun bindDesc(item: MatchPrevious?) {
        vb.tvDescription.apply {
            if (item != null) {
                text = item.description
                visibility = View.VISIBLE
            } else {
                text = ""
                visibility = View.GONE
            }
        }
    }
}
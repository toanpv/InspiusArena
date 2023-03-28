package vn.inspius.toanpv.arena.match.ui.match.list.adapter

import android.os.Bundle
import androidx.core.view.isVisible
import vn.inspius.toanpv.arena.core.ui.widget.loadOrClear
import vn.inspius.toanpv.arena.core.ui.widget.recyclerview.BaseRecyclerViewAdapter
import vn.inspius.toanpv.arena.match.R
import vn.inspius.toanpv.arena.match.databinding.ItemMatchUpcomingBinding
import vn.inspius.toanpv.arena.match.ui.match.model.MatchUpcoming

class MatchUpcomingViewHolder(
    private val vb: ItemMatchUpcomingBinding,
    private val onItemClick: (MatchUpcoming) -> Unit
) :
    BaseRecyclerViewAdapter.BaseViewHolder<MatchUpcoming>(vb) {
    init {
        vb.root.setOnClickListener {
            item?.let { item ->
                onItemClick.invoke(item)
            }
        }
    }

    override fun bind(item: MatchUpcoming?) {
        super.bind(item)
        bindHomeName(item)
        bindAwayName(item)
        bindDate(item)
        bindTime(item)
        bindHomeLogo(item)
        bindAwayLogo(item)
        bindReminder(item)
    }

    override fun bind(item: MatchUpcoming?, payload: Bundle) {
        super.bind(item, payload)
        when {
            payload.getBoolean(
                MatchesRecyclerViewAdapter.PAYLOAD_REMINDER, false
            ) -> bindReminder(item)
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
        }
    }

    private fun bindReminder(match: MatchUpcoming?) {
        vb.ivReminder.apply {
            if (match != null) {
                setImageResource(
                    if (match.reminder) R.drawable.ic_match_reminded
                    else R.drawable.ic_match_not_remind
                )
                isVisible = true
            } else {
                isVisible = false
            }
        }
    }

    private fun bindAwayLogo(match: MatchUpcoming?) {
        vb.ivAwayLogo.loadOrClear(match?.awayLogo)
    }

    private fun bindHomeLogo(match: MatchUpcoming?) {
        vb.ivHomeLogo.loadOrClear(match?.homeLogo)
    }

    private fun bindTime(match: MatchUpcoming?) {
        vb.tvTime.text = match?.time ?: ""
    }

    private fun bindDate(match: MatchUpcoming?) {
        vb.tvDate.text = match?.date ?: ""
    }

    private fun bindAwayName(match: MatchUpcoming?) {
        vb.tvAwayName.text = match?.awayName ?: ""
    }

    private fun bindHomeName(match: MatchUpcoming?) {
        vb.tvHomeName.text = match?.homeName ?: ""
    }
}
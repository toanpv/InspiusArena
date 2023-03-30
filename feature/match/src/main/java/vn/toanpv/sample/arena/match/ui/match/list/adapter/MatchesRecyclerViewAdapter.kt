package vn.toanpv.sample.arena.match.ui.match.list.adapter

import android.os.Bundle
import vn.toanpv.sample.arena.core.ui.widget.recyclerview.BaseRecyclerViewAdapter
import vn.toanpv.sample.arena.match.ui.match.model.MatchUI

abstract class MatchesRecyclerViewAdapter<T : MatchUI, H : BaseRecyclerViewAdapter.BaseViewHolder<T>>(
    private val onItemClick: (T) -> Unit
) :
    BaseRecyclerViewAdapter<T, H>() {

    override fun areItemsSame(oldItem: T, newItem: T): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsSame(oldItem: T, newItem: T): Boolean {
        return oldItem.homeName == newItem.homeName &&
                oldItem.awayName == newItem.awayName &&
                oldItem.homeLogo == newItem.homeLogo &&
                oldItem.awayLogo == newItem.awayLogo &&
                oldItem.date == newItem.date &&
                oldItem.time == newItem.time
    }

    override fun getPayload(oldItem: T, newItem: T): Bundle? {
        return Bundle().apply {
            putBoolean(PAYLOAD_HOME_NAME, oldItem.homeName != newItem.homeName)
            putBoolean(PAYLOAD_AWAY_NAME, oldItem.awayName != newItem.awayName)
            putBoolean(PAYLOAD_HOME_LOGO, oldItem.homeLogo != newItem.homeLogo)
            putBoolean(PAYLOAD_AWAY_LOGO, oldItem.awayLogo != newItem.awayLogo)
            putBoolean(PAYLOAD_DATE, oldItem.date != newItem.date)
            putBoolean(PAYLOAD_TIME, oldItem.time != newItem.time)
        }
    }

    companion object {
        const val PAYLOAD_REMINDER = "payload_reminder"
        const val PAYLOAD_HOME_NAME = "payload_home_name"
        const val PAYLOAD_AWAY_NAME = "payload_away_name"
        const val PAYLOAD_HOME_LOGO = "payload_home_logo"
        const val PAYLOAD_AWAY_LOGO = "payload_away_logo"
        const val PAYLOAD_DATE = "payload_date"
        const val PAYLOAD_TIME = "payload_time"
        const val PAYLOAD_WINNER = "payload_winner"
        const val PAYLOAD_DESCRIPTION = "payload_description"
    }

}
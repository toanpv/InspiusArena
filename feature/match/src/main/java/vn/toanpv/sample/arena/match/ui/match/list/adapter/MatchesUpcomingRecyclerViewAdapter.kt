package vn.toanpv.sample.arena.match.ui.match.list.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import vn.toanpv.sample.arena.match.databinding.ItemMatchUpcomingBinding
import vn.toanpv.sample.arena.match.ui.match.model.MatchUpcoming

class MatchesUpcomingRecyclerViewAdapter(private val onItemClick: (MatchUpcoming) -> Unit) :
    MatchesRecyclerViewAdapter<MatchUpcoming, MatchUpcomingViewHolder>(onItemClick) {

    override fun areItemsSame(oldItem: MatchUpcoming, newItem: MatchUpcoming): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsSame(oldItem: MatchUpcoming, newItem: MatchUpcoming): Boolean {
        return oldItem.reminder == newItem.reminder &&
                super.areContentsSame(oldItem, newItem)
    }

    override fun getPayload(oldItem: MatchUpcoming, newItem: MatchUpcoming): Bundle? {
        return super.getPayload(oldItem, newItem)?.apply {
            putBoolean(PAYLOAD_REMINDER, oldItem.reminder != newItem.reminder)
        }
    }

    override fun createHolder(parent: ViewGroup, viewType: Int): MatchUpcomingViewHolder {
        return MatchUpcomingViewHolder(
            ItemMatchUpcomingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onItemClick
        )
    }

}
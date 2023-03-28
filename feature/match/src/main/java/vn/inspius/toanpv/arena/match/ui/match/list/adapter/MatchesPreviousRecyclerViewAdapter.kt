package vn.inspius.toanpv.arena.match.ui.match.list.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import vn.inspius.toanpv.arena.match.databinding.ItemMatchPreviousBinding
import vn.inspius.toanpv.arena.match.ui.match.model.MatchPrevious

class MatchesPreviousRecyclerViewAdapter(private val onItemClick: (MatchPrevious) -> Unit) :
    MatchesRecyclerViewAdapter<MatchPrevious, MatchPreviousViewHolder>(onItemClick) {

    override fun areItemsSame(oldItem: MatchPrevious, newItem: MatchPrevious): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsSame(oldItem: MatchPrevious, newItem: MatchPrevious): Boolean {
        return oldItem.winner == newItem.winner &&
                oldItem.description == newItem.description &&
                super.areContentsSame(oldItem, newItem)
    }

    override fun getPayload(oldItem: MatchPrevious, newItem: MatchPrevious): Bundle? {
        return super.getPayload(oldItem, newItem)?.apply {
            putBoolean(PAYLOAD_REMINDER, oldItem.winner != newItem.winner)
            putBoolean(PAYLOAD_DESCRIPTION, oldItem.description != newItem.description)
        }
    }

    override fun createHolder(parent: ViewGroup, viewType: Int): MatchPreviousViewHolder {
        return MatchPreviousViewHolder(
            ItemMatchPreviousBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onItemClick
        )
    }

}
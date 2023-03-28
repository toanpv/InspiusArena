package vn.inspius.toanpv.arena.match.ui.team.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import vn.inspius.toanpv.arena.core.ui.widget.recyclerview.BaseRecyclerViewAdapter
import vn.inspius.toanpv.arena.match.databinding.ItemTeamSelectableBinding
import vn.inspius.toanpv.arena.match.ui.team.model.TeamItem

class TeamSelectorAdapter(private val onItemClick: (TeamItem) -> Unit) :
    BaseRecyclerViewAdapter<TeamItem, TeamSelectorViewHolder>() {
    override fun areItemsSame(oldItem: TeamItem, newItem: TeamItem): Boolean {
        return oldItem.id != newItem.id
    }

    override fun areContentsSame(oldItem: TeamItem, newItem: TeamItem): Boolean {
        return oldItem.checked != newItem.checked || oldItem.logo != newItem.logo || oldItem.name != newItem.name
    }

    override fun getPayload(oldItem: TeamItem, newItem: TeamItem): Bundle {
        return Bundle().apply {
            putBoolean(PAYLOAD_CHECK, oldItem.checked != newItem.checked)
            putBoolean(PAYLOAD_LOGO, oldItem.logo != newItem.logo)
            putBoolean(PAYLOAD_NAME, oldItem.name != newItem.name)
        }
    }

    override fun createHolder(parent: ViewGroup, viewType: Int): TeamSelectorViewHolder {
        return TeamSelectorViewHolder(
            ItemTeamSelectableBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onItemClick
        )
    }

    companion object {
        const val PAYLOAD_CHECK = "payload_check"
        const val PAYLOAD_NAME = "payload_name"
        const val PAYLOAD_LOGO = "payload_logo"
    }
}
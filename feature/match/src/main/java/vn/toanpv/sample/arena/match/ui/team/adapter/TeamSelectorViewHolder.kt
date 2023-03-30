package vn.toanpv.sample.arena.match.ui.team.adapter

import android.os.Bundle
import androidx.core.view.isVisible
import coil.load
import coil.transform.CircleCropTransformation
import vn.toanpv.sample.arena.core.ui.widget.recyclerview.BaseRecyclerViewAdapter
import vn.toanpv.sample.arena.match.databinding.ItemTeamSelectableBinding
import vn.toanpv.sample.arena.match.ui.team.adapter.TeamSelectorAdapter.Companion.PAYLOAD_CHECK
import vn.toanpv.sample.arena.match.ui.team.adapter.TeamSelectorAdapter.Companion.PAYLOAD_LOGO
import vn.toanpv.sample.arena.match.ui.team.adapter.TeamSelectorAdapter.Companion.PAYLOAD_NAME
import vn.toanpv.sample.arena.match.ui.team.model.TeamItem

class TeamSelectorViewHolder(
    private val vb: ItemTeamSelectableBinding,
    private val onItemClick: (TeamItem) -> Unit
) :
    BaseRecyclerViewAdapter.BaseViewHolder<TeamItem>(vb) {
    init {
        vb.root.setOnClickListener {
            item?.let { item ->
                onItemClick.invoke(item)
            }
        }
    }

    override fun bind(item: TeamItem?) {
        super.bind(item)
        bindName(item)
        bindLogo(item)
        bindCheck(item)
    }

    override fun bind(item: TeamItem?, payload: Bundle) {
        super.bind(item, payload)
        when {
            payload.getBoolean(PAYLOAD_CHECK, false) -> bindCheck(item)
            payload.getBoolean(PAYLOAD_NAME, false) -> bindName(item)
            payload.getBoolean(PAYLOAD_LOGO, false) -> bindLogo(item)
        }
    }

    private fun bindName(item: TeamItem?) {
        vb.tvName.text = item?.name ?: ""
    }

    private fun bindLogo(item: TeamItem?) {
        if (item != null && !item.logo.isNullOrEmpty()) {
            vb.ivLogo.load(item.logo) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }
    }

    private fun bindCheck(item: TeamItem?) {
        vb.ivCheck.isVisible = item?.checked ?: false
    }
}
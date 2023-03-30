package vn.toanpv.sample.arena.match.ui.team.model

import vn.toanpv.sample.arena.entity.Team

class TeamItem {
    companion object {
        const val ID_ALL = "-1"
    }

    var id: String = ""
    var name: String = ""
    var logo: String? = null
    var checked: Boolean = false

    fun toItem() = Team(id, name, logo)
}

fun Team.toItemList(idSelected: String? = null) = TeamItem().apply {
    id = this@toItemList.id
    name = this@toItemList.name
    logo = this@toItemList.logo
    checked = id == idSelected
}
package vn.inspius.toanpv.arena.match.repository.data.local.entity

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import vn.inspius.toanpv.arena.entity.Team
import vn.inspius.toanpv.arena.repository.data.EntityMapper

class TeamRealm : RealmObject, EntityMapper<Team, TeamRealm> {
    @PrimaryKey
    var id: String = ""
    var name: String = ""
    var logo: String? = null
    override fun convert(): Team {
        return Team(id, name, logo)
    }
}

fun Team.toRealm(): TeamRealm {
    return TeamRealm().apply {
        id = this@toRealm.id
        name = this@toRealm.name
        logo = this@toRealm.logo
    }
}
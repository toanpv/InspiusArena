package vn.toanpv.sample.arena.match.repository.data.local.entity

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import vn.toanpv.sample.arena.entity.Team
import vn.toanpv.sample.arena.repository.data.EntityMapper

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
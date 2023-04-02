package vn.toanpv.sample.arena.match.repository.data.local.entity

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import vn.toanpv.sample.arena.entity.Match
import vn.toanpv.sample.repository.data.EntityMapper

class MatchRealm : RealmObject, EntityMapper<Match, MatchRealm> {
    @PrimaryKey
    var id: String = ""
    var date: String = ""
    var description: String = ""
    var home: String = ""
    var away: String = ""
    var winner: String? = null
    var highlights: String? = null
    var homeId: String = ""
    var awayId: String = ""
    var winnerId: String = ""
    override fun convert(): Match {
        return Match(
            id,
            date,
            description,
            home,
            away,
            winner,
            highlights,
            homeId,
            awayId,
            winnerId
        )
    }
}

fun Match.toRealm(): MatchRealm {
    return MatchRealm().apply {
        id = this@toRealm.id
        date = this@toRealm.date
        description = this@toRealm.description
        home = this@toRealm.home
        away = this@toRealm.away
        winner = this@toRealm.winner
        highlights = this@toRealm.highlights
        homeId = this@toRealm.homeId
        awayId = this@toRealm.awayId
        winnerId = this@toRealm.winnerId
    }
}
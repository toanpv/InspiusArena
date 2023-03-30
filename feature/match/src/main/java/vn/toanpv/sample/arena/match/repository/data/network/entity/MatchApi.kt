package vn.toanpv.sample.arena.match.repository.data.network.entity

import io.ktor.resources.*
import kotlinx.serialization.Serializable
import vn.toanpv.sample.arena.entity.Match
import vn.toanpv.sample.arena.repository.data.EntityMapper

@Resource("teams/matches")
@Serializable
class MatchesApi {
    val matches: Matches? = null
}

@Serializable
class Matches(
    val previous: List<MatchApi> = mutableListOf(), val upcoming: List<MatchApi> = mutableListOf()
)

@Serializable
data class MatchApi(
    val date: String,
    val description: String,
    val home: String,
    val away: String,
    val winner: String? = null,
    val highlights: String? = null
) : EntityMapper<Match, MatchApi> {
    override fun convert(): Match {
        return Match("", date, description, home, away, winner, highlights)
    }

}
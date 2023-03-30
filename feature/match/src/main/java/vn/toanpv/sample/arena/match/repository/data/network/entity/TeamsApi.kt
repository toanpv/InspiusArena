package vn.toanpv.sample.arena.match.repository.data.network.entity

import io.ktor.resources.*
import kotlinx.serialization.Serializable
import vn.toanpv.sample.arena.entity.Team
import vn.toanpv.sample.arena.repository.data.EntityMapper

@Resource("teams")
@Serializable
class TeamsApi {
    val teams: List<TeamApi> = mutableListOf()

    @Resource("{id}/matches")
    class Matches(val parent: TeamsApi = TeamsApi(), val id: String) {
        val matches: Matches? = null
    }
}

@Serializable
data class TeamApi(
    val id: String, val name: String, val logo: String?
) : EntityMapper<Team, TeamApi> {
    override fun convert(): Team {
        return Team(id, name, logo)
    }
}
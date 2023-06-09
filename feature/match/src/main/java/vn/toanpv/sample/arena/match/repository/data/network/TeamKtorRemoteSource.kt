package vn.toanpv.sample.arena.match.repository.data.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.resources.*
import vn.toanpv.sample.arena.entity.Match
import vn.toanpv.sample.arena.entity.Team
import vn.toanpv.sample.arena.match.repository.data.network.entity.Matches
import vn.toanpv.sample.arena.match.repository.data.network.entity.MatchesApi
import vn.toanpv.sample.arena.match.repository.data.network.entity.TeamsApi
import vn.toanpv.sample.arena.repository.data.network.TeamRemoteSource

class TeamKtorRemoteSource(private val client: HttpClient) : TeamRemoteSource {

    override suspend fun getTeams(): List<Team> {
        return (client.get(TeamsApi()).body() as TeamsApi).teams.map { it.convert() }
    }

    override suspend fun getMatches(): List<Match> {
        return (client.get(MatchesApi())
            .body() as MatchesApi).matches?.let { it.previous + it.upcoming }
            ?.map { it.convert() } ?: emptyList()
    }

    override suspend fun getAllMatchesByTeam(teamId: String): List<Match> {
        return (client.get(TeamsApi.Matches(id = teamId))
            .body() as Matches).let { it.previous + it.upcoming }.map { it.convert() }
    }

}
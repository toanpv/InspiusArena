package vn.toanpv.sample.arena.repository.data.local

import vn.toanpv.sample.arena.entity.Match
import vn.toanpv.sample.arena.entity.Team

interface TeamLocalSource {
    suspend fun getTeams(): List<Team>

    suspend fun getTeamsByName(name: String, top: Int? = null): List<Team>

    suspend fun updateTeams(teams: List<Team>)

    suspend fun deleteTeams()

    suspend fun getMatches(): List<Match>

    suspend fun deleteMatches()

    suspend fun updateMatches(matches: List<Match>)

    suspend fun getAllMatchesByTeam(teamId: String): List<Match>

    suspend fun getUpcomingMatches(teamId: String?): List<Match>

    suspend fun getPreviousMatches(teamId: String?): List<Match>

    suspend fun getTopLatestMatches(top: Int? = 3): List<Match>
}
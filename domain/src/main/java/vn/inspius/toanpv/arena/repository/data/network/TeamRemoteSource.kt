package vn.inspius.toanpv.arena.repository.data.network

import vn.inspius.toanpv.arena.entity.Match
import vn.inspius.toanpv.arena.entity.Team

interface TeamRemoteSource {
    suspend fun getTeams(): List<Team>

    suspend fun getMatches(): List<Match>

    suspend fun getAllMatchesByTeam(teamId: String): List<Match>

}
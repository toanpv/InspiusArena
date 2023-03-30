package vn.toanpv.sample.arena.repository.data.network

import vn.toanpv.sample.arena.entity.Match
import vn.toanpv.sample.arena.entity.Team

interface TeamRemoteSource {
    suspend fun getTeams(): List<Team>

    suspend fun getMatches(): List<Match>

    suspend fun getAllMatchesByTeam(teamId: String): List<Match>

}
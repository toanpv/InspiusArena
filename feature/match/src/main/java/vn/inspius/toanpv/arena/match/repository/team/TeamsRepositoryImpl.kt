package vn.inspius.toanpv.arena.match.repository.team

import vn.inspius.toanpv.arena.entity.Team
import vn.inspius.toanpv.arena.extension.suspendRunCatching
import vn.inspius.toanpv.arena.match.util.LogUtils
import vn.inspius.toanpv.arena.repository.data.local.TeamLocalSource
import vn.inspius.toanpv.arena.repository.data.network.TeamRemoteSource
import vn.inspius.toanpv.arena.repository.team.TeamsRepository

class TeamsRepositoryImpl(
    private val network: TeamRemoteSource,
    private val local: TeamLocalSource
) : TeamsRepository {
    override suspend fun getTeams(): List<Team> {
        return local.getTeams()
    }

    override suspend fun sync(): Boolean {
        return suspendRunCatching {
            LogUtils.d("TeamsRepositoryImpl start")
            val teams = network.getTeams()
            local.deleteTeams()
            local.updateTeams(teams)
        }.isSuccess
    }
}
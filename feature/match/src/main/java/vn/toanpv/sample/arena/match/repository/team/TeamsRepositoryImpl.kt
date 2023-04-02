package vn.toanpv.sample.arena.match.repository.team

import vn.toanpv.sample.arena.entity.Team
import vn.toanpv.sample.arena.extension.suspendRunCatching
import vn.toanpv.sample.arena.core.ui.util.LogUtils
import vn.toanpv.sample.arena.repository.data.local.TeamLocalSource
import vn.toanpv.sample.arena.repository.data.network.TeamRemoteSource
import vn.toanpv.sample.arena.repository.team.TeamsRepository

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
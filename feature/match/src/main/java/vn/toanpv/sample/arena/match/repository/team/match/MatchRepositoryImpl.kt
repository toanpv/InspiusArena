package vn.toanpv.sample.arena.match.repository.team.match

import kotlinx.coroutines.flow.Flow
import vn.toanpv.sample.arena.entity.Match
import vn.toanpv.sample.arena.extension.md5
import vn.toanpv.sample.arena.extension.suspendRunCatching
import vn.toanpv.sample.arena.match.util.LogUtils
import vn.toanpv.sample.arena.repository.data.local.TeamLocalSource
import vn.toanpv.sample.arena.repository.data.network.TeamRemoteSource
import vn.toanpv.sample.arena.repository.data.preferences.DataStoreSource
import vn.toanpv.sample.arena.repository.team.match.MatchRepository

class MatchRepositoryImpl(
    private val network: TeamRemoteSource,
    private val local: TeamLocalSource,
    private val dataStoreSource: DataStoreSource
) : MatchRepository {
    override suspend fun getMatches(): List<Match> {
        return local.getMatches()
    }

    override suspend fun getAllMatchesByTeam(teamId: String): List<Match> {
        return local.getAllMatchesByTeam(teamId)
    }

    override suspend fun getUpcomingMatches(teamId: String?): List<Match> {
        return local.getUpcomingMatches(teamId)
    }

    override suspend fun getPreviousMatches(teamId: String?): List<Match> {
        return local.getPreviousMatches(teamId)
    }

    override suspend fun getTopLatestMatches(top: Int?): List<Match> {
        return local.getTopLatestMatches(top)
    }

    override suspend fun getReminderMatchIds(): Flow<Set<String>> {
        return dataStoreSource.getReminderMatchIds()
    }

    override suspend fun updateReminderMatchIds(ids: Set<String>): Boolean {
        return dataStoreSource.updateReminderMatchIds(ids)
    }

    override suspend fun sync(): Boolean {
        return suspendRunCatching {
            LogUtils.d("MatchRepo sync")
            val teams = local.getTeams()
            val teamMap = teams.associateBy { it.name }
            network.getMatches().onEach {
                it.apply {
                    homeId = teamMap[home.trim()]?.id ?: ""
                    awayId = teamMap[away.trim()]?.id ?: ""
                    winnerId = teamMap[winner?.trim()]?.id ?: ""
                    id = "$homeId$awayId$date".md5()
                }
            }.let {
                local.deleteMatches()
                local.updateMatches(it)
            }
        }.isSuccess
    }
}
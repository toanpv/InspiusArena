package vn.inspius.toanpv.arena.match.repository.data.local

import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.copyFromRealm
import io.realm.kotlin.ext.query
import vn.inspius.toanpv.arena.entity.Match
import vn.inspius.toanpv.arena.entity.Team
import vn.inspius.toanpv.arena.match.repository.data.local.entity.MatchRealm
import vn.inspius.toanpv.arena.match.repository.data.local.entity.TeamRealm
import vn.inspius.toanpv.arena.match.repository.data.local.entity.toRealm
import vn.inspius.toanpv.arena.repository.data.local.TeamLocalSource

class RealmTeamLocalSource(private val realm: Realm) : TeamLocalSource {

    override suspend fun getTeams(): List<Team> {
        return realm.query<TeamRealm>().find().copyFromRealm().map { it.convert() }
    }

    override suspend fun getTeamsByName(name: String, top: Int?): List<Team> {
        val query = realm.query<TeamRealm>("name CONTAINS[c] '$name'")
        return (if (top != null) {
            query.limit(top)
        } else query).find().copyFromRealm().map { it.convert() }
    }

    override suspend fun updateTeams(teams: List<Team>) {
        realm.writeBlocking {
            teams.forEach { copyToRealm(it.toRealm(), UpdatePolicy.ALL) }
        }
    }

    override suspend fun deleteTeams() {
        realm.deleteAll<TeamRealm>()
    }

    override suspend fun getMatches(): List<Match> {
        return realm.query<MatchRealm>().find().copyFromRealm().map { it.convert() }
    }

    override suspend fun deleteMatches() {
        realm.deleteAll<MatchRealm>()
    }

    override suspend fun updateMatches(matches: List<Match>) {
        realm.writeBlocking {
            matches.forEach { copyToRealm(it.toRealm(), UpdatePolicy.ALL) }
        }
    }

    override suspend fun getAllMatchesByTeam(teamId: String): List<Match> {
        return realm.query<MatchRealm>("homeId = '$teamId' OR awayId = '$teamId'").find()
            .map { it.convert() }
    }

    override suspend fun getUpcomingMatches(teamId: String?): List<Match> {
        val teamQuery =
            if (teamId.isNullOrEmpty()) "" else "homeId = '$teamId' OR awayId = '$teamId' AND"
        return realm.query<MatchRealm>("$teamQuery winner = NULL").find().map { it.convert() }
    }

    override suspend fun getPreviousMatches(teamId: String?): List<Match> {
        val teamQuery =
            if (teamId.isNullOrEmpty()) "" else "homeId = '$teamId' OR awayId = '$teamId' AND"
        return realm.query<MatchRealm>("$teamQuery winner != nil").find().map { it.convert() }
    }

    override suspend fun getTopLatestMatches(top: Int?): List<Match> {
        return realm.query<MatchRealm>().limit(top ?: 3).find().map { it.convert() }
    }
}
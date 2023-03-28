package vn.inspius.toanpv.arena.repository.team

import vn.inspius.toanpv.arena.entity.Team
import vn.inspius.toanpv.arena.repository.data.Syncable

/**
 * The `TeamsRepository` interface represents a repository for managing and retrieving data related to teams.
 */
interface TeamsRepository : Syncable {

    /**
     * Retrieves all the teams from the repository.
     *
     * @return A list of `Team` objects representing all the teams in the repository.
     */
    suspend fun getTeams(): List<Team>
}

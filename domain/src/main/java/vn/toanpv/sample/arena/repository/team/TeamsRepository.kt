package vn.toanpv.sample.arena.repository.team

import vn.toanpv.sample.arena.entity.Team
import vn.toanpv.sample.arena.repository.data.Syncable

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

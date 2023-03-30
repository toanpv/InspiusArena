package vn.toanpv.sample.arena.repository.team.match

import kotlinx.coroutines.flow.Flow
import vn.toanpv.sample.arena.entity.Match
import vn.toanpv.sample.arena.repository.data.Syncable

/**
 * The `MatchRepository` interface represents a repository for managing and retrieving data related to matches.
 */
interface MatchRepository : Syncable {

    /**
     * Retrieves all the matches from the repository.
     *
     * @return A list of `Match` objects representing all the matches in the repository.
     */
    suspend fun getMatches(): List<Match>

    /**
     * Retrieves all the matches associated with a specific team.
     *
     * @param teamId The unique identifier of the team to retrieve matches for.
     * @return A list of `Match` objects representing all the matches associated with the specified team.
     */
    suspend fun getAllMatchesByTeam(teamId: String): List<Match>

    /**
     * Retrieves all the upcoming matches from the repository.
     *
     * @return A list of `Match` objects representing all the upcoming matches in the repository.
     */
    suspend fun getUpcomingMatches(teamId: String?): List<Match>

    /**
     * Retrieves all the previous matches from the repository.
     *
     * @return A list of `Match` objects representing all the previous matches in the repository.
     */
    suspend fun getPreviousMatches(teamId: String?): List<Match>

    /**
     * Retrieves the top latest matches from the repository.
     *
     * @param top The number of top latest matches to retrieve. If not specified, the default value is 3.
     * @return A list of `Match` objects representing the top latest matches in the repository.
     */
    suspend fun getTopLatestMatches(top: Int? = 3): List<Match>


    suspend fun getReminderMatchIds(): Flow<Set<String>>

    suspend fun updateReminderMatchIds(ids: Set<String>): Boolean
}

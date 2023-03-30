package vn.toanpv.sample.arena.domain.team.sync

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import vn.toanpv.sample.arena.domain.Interact
import vn.toanpv.sample.arena.repository.data.preferences.DataStoreSource
import vn.toanpv.sample.arena.repository.team.TeamsRepository
import vn.toanpv.sample.arena.repository.team.match.MatchRepository

class SyncDataInteractImpl(
    private val teamsRepository: TeamsRepository,
    private val matchRepository: MatchRepository,
    private val dataStoreSource: DataStoreSource
) : SyncDataInteract {
    override suspend fun execute(param: Interact.Param?): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                dataStoreSource.setSyncStatus(true)
                if (!async { teamsRepository.sync() }.await())
                    return@withContext false

                if (!async { matchRepository.sync() }.await()) {
                    return@withContext false
                }
                dataStoreSource.setSyncStatus(false)
                true
            } catch (ex: Exception) {
                dataStoreSource.setSyncStatus(false)
                false
            }
        }
    }
}
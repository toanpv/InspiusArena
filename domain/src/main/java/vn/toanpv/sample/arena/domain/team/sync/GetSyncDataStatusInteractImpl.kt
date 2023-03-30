package vn.toanpv.sample.arena.domain.team.sync

import kotlinx.coroutines.flow.Flow
import vn.toanpv.sample.arena.domain.Interact
import vn.toanpv.sample.arena.repository.data.preferences.DataStoreSource

class GetSyncDataStatusInteractImpl(private val dataStoreSource: DataStoreSource) :
    GetSyncDataStatusInteract {
    override suspend fun execute(param: Interact.Param?): Flow<Boolean> {
        return dataStoreSource.getSyncStatus()
    }
}
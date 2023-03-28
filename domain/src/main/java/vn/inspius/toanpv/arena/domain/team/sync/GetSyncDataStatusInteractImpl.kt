package vn.inspius.toanpv.arena.domain.team.sync

import kotlinx.coroutines.flow.Flow
import vn.inspius.toanpv.arena.domain.Interact
import vn.inspius.toanpv.arena.repository.data.preferences.DataStoreSource

class GetSyncDataStatusInteractImpl(private val dataStoreSource: DataStoreSource) :
    GetSyncDataStatusInteract {
    override suspend fun execute(param: Interact.Param?): Flow<Boolean> {
        return dataStoreSource.getSyncStatus()
    }
}
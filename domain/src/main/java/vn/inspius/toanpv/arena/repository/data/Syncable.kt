package vn.inspius.toanpv.arena.repository.data

/**
 * Represents a generic interface for synchronizable repositories.
 */
interface Syncable {

    /**
     * Synchronizes the state of the implementing object with its external source.
     *
     * @return true if the synchronization was successful, false otherwise.
     * @throws Exception if there is an error during synchronization.
     */
    suspend fun sync(): Boolean
}

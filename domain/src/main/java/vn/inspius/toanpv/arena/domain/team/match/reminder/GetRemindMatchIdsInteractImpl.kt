package vn.inspius.toanpv.arena.domain.team.match.reminder

import kotlinx.coroutines.flow.Flow
import vn.inspius.toanpv.arena.domain.Interact
import vn.inspius.toanpv.arena.repository.team.match.MatchRepository

class GetRemindMatchIdsInteractImpl(private val matchRepository: MatchRepository) :
    GetRemindMatchIdsInteract {
    override suspend fun execute(param: Interact.Param?): Flow<Set<String>> {
        return matchRepository.getReminderMatchIds()
    }

}
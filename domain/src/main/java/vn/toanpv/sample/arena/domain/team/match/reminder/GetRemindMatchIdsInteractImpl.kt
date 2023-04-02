package vn.toanpv.sample.arena.domain.team.match.reminder

import kotlinx.coroutines.flow.Flow
import vn.toanpv.sample.Interact
import vn.toanpv.sample.arena.repository.team.match.MatchRepository

class GetRemindMatchIdsInteractImpl(private val matchRepository: MatchRepository) :
    GetRemindMatchIdsInteract {
    override suspend fun execute(param: Interact.Param?): Flow<Set<String>> {
        return matchRepository.getReminderMatchIds()
    }

}
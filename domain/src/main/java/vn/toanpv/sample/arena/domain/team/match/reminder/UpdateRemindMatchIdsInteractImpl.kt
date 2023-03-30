package vn.toanpv.sample.arena.domain.team.match.reminder

import vn.toanpv.sample.arena.repository.team.match.MatchRepository

class UpdateRemindMatchIdsInteractImpl(private val matchRepository: MatchRepository) :
    UpdateRemindMatchIdsInteract {
    override suspend fun execute(param: UpdateRemindMatchIdsInteract.Param?): Boolean {
        param?.ids?.let {
            matchRepository.updateReminderMatchIds(it)
            return true
        }
        return false
    }
}
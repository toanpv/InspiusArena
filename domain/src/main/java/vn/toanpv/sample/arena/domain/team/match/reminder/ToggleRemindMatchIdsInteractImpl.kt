package vn.toanpv.sample.arena.domain.team.match.reminder

import kotlinx.coroutines.flow.first

class ToggleRemindMatchIdsInteractImpl(
    private val getRemindMatchIdsInteract: GetRemindMatchIdsInteract,
    private val updateRemindMatchIdsInteract: UpdateRemindMatchIdsInteract
) : ToggleRemindMatchIdsInteract {
    override suspend fun execute(param: ToggleRemindMatchIdsInteract.Param?): Boolean {
        if (param == null) throw IllegalArgumentException("ToggleRemindMatchIdsInteract.Param is required")
        val reminderList =
            getRemindMatchIdsInteract.execute().first().toMutableSet()
        val actionComplete: Boolean
        val result = if (reminderList.contains(param.matchId)) {
            actionComplete = reminderList.remove(param.matchId)
            false
        } else {
            actionComplete = reminderList.add(param.matchId)
            true
        }
        updateRemindMatchIdsInteract.execute(UpdateRemindMatchIdsInteract.Param(reminderList))
        return result && actionComplete
    }
}
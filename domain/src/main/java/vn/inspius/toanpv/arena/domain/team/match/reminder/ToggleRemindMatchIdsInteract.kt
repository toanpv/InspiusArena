package vn.inspius.toanpv.arena.domain.team.match.reminder

import vn.inspius.toanpv.arena.domain.Interact

interface ToggleRemindMatchIdsInteract :
    Interact<ToggleRemindMatchIdsInteract.Param, Boolean> {
    data class Param(val matchId: String) : Interact.Param()
}
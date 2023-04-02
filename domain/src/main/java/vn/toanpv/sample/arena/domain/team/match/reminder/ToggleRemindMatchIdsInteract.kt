package vn.toanpv.sample.arena.domain.team.match.reminder

import vn.toanpv.sample.Interact

interface ToggleRemindMatchIdsInteract :
    Interact<ToggleRemindMatchIdsInteract.Param, Boolean> {
    data class Param(val matchId: String) : Interact.Param()
}
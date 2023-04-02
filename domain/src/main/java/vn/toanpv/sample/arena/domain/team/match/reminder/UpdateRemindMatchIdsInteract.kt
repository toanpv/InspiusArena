package vn.toanpv.sample.arena.domain.team.match.reminder

import vn.toanpv.sample.Interact

interface UpdateRemindMatchIdsInteract :
    Interact<UpdateRemindMatchIdsInteract.Param, Boolean> {
    data class Param(val ids: Set<String>) : Interact.Param()
}
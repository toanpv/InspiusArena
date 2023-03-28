package vn.inspius.toanpv.arena.domain.team.match.reminder

import vn.inspius.toanpv.arena.domain.Interact

interface UpdateRemindMatchIdsInteract :
    Interact<UpdateRemindMatchIdsInteract.Param, Boolean> {
    data class Param(val ids: Set<String>) : Interact.Param()
}
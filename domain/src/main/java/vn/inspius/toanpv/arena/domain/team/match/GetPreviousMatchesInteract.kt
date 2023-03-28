package vn.inspius.toanpv.arena.domain.team.match

import vn.inspius.toanpv.arena.domain.Interact
import vn.inspius.toanpv.arena.entity.Match

interface GetPreviousMatchesInteract : Interact<GetPreviousMatchesInteract.Param, List<Match>> {
    data class Param(val teamId: String?) : Interact.Param()
}
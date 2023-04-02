package vn.toanpv.sample.arena.domain.team.match

import vn.toanpv.sample.Interact
import vn.toanpv.sample.arena.entity.Match

interface GetUpcomingMatchesInteract : Interact<GetUpcomingMatchesInteract.Param, List<Match>> {
    data class Param(val teamId: String?) : Interact.Param()
}
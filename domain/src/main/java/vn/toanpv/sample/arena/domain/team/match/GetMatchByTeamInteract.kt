package vn.toanpv.sample.arena.domain.team.match

import vn.toanpv.sample.arena.domain.Interact
import vn.toanpv.sample.arena.entity.Match

interface GetMatchByTeamInteract : Interact<GetMatchByTeamInteract.Param, List<Match>> {
    data class Param(val teamId: String?) : Interact.Param()
}
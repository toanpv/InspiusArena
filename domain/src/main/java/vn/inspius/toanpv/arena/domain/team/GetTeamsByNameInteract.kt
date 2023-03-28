package vn.inspius.toanpv.arena.domain.team

import vn.inspius.toanpv.arena.domain.Interact
import vn.inspius.toanpv.arena.entity.Team

interface GetTeamsByNameInteract : Interact<GetTeamsByNameInteract.Param, List<Team>> {
    data class Param(val query: String, val top: Int? = null) : Interact.Param()
}


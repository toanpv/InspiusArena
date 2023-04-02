package vn.toanpv.sample.arena.domain.team

import vn.toanpv.sample.Interact
import vn.toanpv.sample.arena.entity.Team

interface GetTeamsByNameInteract : Interact<GetTeamsByNameInteract.Param, List<Team>> {
    data class Param(val query: String, val top: Int? = null) : Interact.Param()
}


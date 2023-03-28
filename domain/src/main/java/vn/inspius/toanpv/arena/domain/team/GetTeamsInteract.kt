package vn.inspius.toanpv.arena.domain.team

import vn.inspius.toanpv.arena.domain.Interact
import vn.inspius.toanpv.arena.entity.Team

interface GetTeamsInteract : Interact<Interact.Param, List<Team>>


package vn.toanpv.sample.arena.domain.team

import vn.toanpv.sample.arena.domain.Interact
import vn.toanpv.sample.arena.entity.Team

interface GetTeamsInteract : Interact<Interact.Param, List<Team>>


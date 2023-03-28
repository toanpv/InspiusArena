package vn.inspius.toanpv.arena.domain.team.match

import vn.inspius.toanpv.arena.domain.Interact
import vn.inspius.toanpv.arena.entity.Match

interface GetMatchesInteract : Interact<Interact.Param, List<Match>> {

}
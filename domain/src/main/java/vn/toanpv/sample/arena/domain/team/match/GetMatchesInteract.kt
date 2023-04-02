package vn.toanpv.sample.arena.domain.team.match

import vn.toanpv.sample.Interact
import vn.toanpv.sample.arena.entity.Match

interface GetMatchesInteract : Interact<Interact.Param, List<Match>> {

}
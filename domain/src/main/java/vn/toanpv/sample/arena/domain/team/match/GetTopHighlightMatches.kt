package vn.toanpv.sample.arena.domain.team.match

import vn.toanpv.sample.arena.domain.Interact
import vn.toanpv.sample.arena.entity.Match

interface GetTopHighlightMatches : Interact<GetTopHighlightMatches.Param, List<Match>> {
    data class Param(val top: Int) : Interact.Param()
}
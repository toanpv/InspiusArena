package vn.inspius.toanpv.arena.domain.team.match

import vn.inspius.toanpv.arena.domain.Interact
import vn.inspius.toanpv.arena.entity.Match

interface GetTopHighlightMatches : Interact<GetTopHighlightMatches.Param, List<Match>> {
    data class Param(val top: Int) : Interact.Param()
}
package vn.inspius.toanpv.arena.domain.team.match

import vn.inspius.toanpv.arena.domain.Interact
import vn.inspius.toanpv.arena.entity.Match
import vn.inspius.toanpv.arena.repository.team.match.MatchRepository

class GetMatchesInteractImpl(private val matchRepository: MatchRepository) : GetMatchesInteract {
    override suspend fun execute(param: Interact.Param?): List<Match> {
        return matchRepository.getMatches()
    }
}
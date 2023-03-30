package vn.toanpv.sample.arena.domain.team.match

import vn.toanpv.sample.arena.domain.Interact
import vn.toanpv.sample.arena.entity.Match
import vn.toanpv.sample.arena.repository.team.match.MatchRepository

class GetMatchesInteractImpl(private val matchRepository: MatchRepository) : GetMatchesInteract {
    override suspend fun execute(param: Interact.Param?): List<Match> {
        return matchRepository.getMatches()
    }
}
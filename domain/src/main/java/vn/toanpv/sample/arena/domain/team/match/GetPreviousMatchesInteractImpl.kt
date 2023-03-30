package vn.toanpv.sample.arena.domain.team.match

import vn.toanpv.sample.arena.entity.Match
import vn.toanpv.sample.arena.repository.team.match.MatchRepository

class GetPreviousMatchesInteractImpl(private val matchRepository: MatchRepository) : GetPreviousMatchesInteract {
    override suspend fun execute(param: GetPreviousMatchesInteract.Param?): List<Match> {
        return matchRepository.getPreviousMatches(param?.teamId)
    }
}
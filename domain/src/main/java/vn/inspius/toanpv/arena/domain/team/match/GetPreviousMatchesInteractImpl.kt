package vn.inspius.toanpv.arena.domain.team.match

import vn.inspius.toanpv.arena.entity.Match
import vn.inspius.toanpv.arena.repository.team.match.MatchRepository

class GetPreviousMatchesInteractImpl(private val matchRepository: MatchRepository) : GetPreviousMatchesInteract {
    override suspend fun execute(param: GetPreviousMatchesInteract.Param?): List<Match> {
        return matchRepository.getPreviousMatches(param?.teamId)
    }
}
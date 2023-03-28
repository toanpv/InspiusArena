package vn.inspius.toanpv.arena.domain.team.match

import vn.inspius.toanpv.arena.entity.Match
import vn.inspius.toanpv.arena.repository.team.match.MatchRepository

class GetUpcomingMatchesInteractImpl(private val matchRepository: MatchRepository) : GetUpcomingMatchesInteract {
    override suspend fun execute(param: GetUpcomingMatchesInteract.Param?): List<Match> {
        return matchRepository.getUpcomingMatches(param?.teamId)
    }
}
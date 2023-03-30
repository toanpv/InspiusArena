package vn.toanpv.sample.arena.domain.team.match

import vn.toanpv.sample.arena.entity.Match
import vn.toanpv.sample.arena.repository.team.match.MatchRepository

class GetUpcomingMatchesInteractImpl(private val matchRepository: MatchRepository) : GetUpcomingMatchesInteract {
    override suspend fun execute(param: GetUpcomingMatchesInteract.Param?): List<Match> {
        return matchRepository.getUpcomingMatches(param?.teamId)
    }
}
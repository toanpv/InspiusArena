package vn.toanpv.sample.arena.domain.team.match

import vn.toanpv.sample.arena.entity.Match
import vn.toanpv.sample.arena.repository.team.match.MatchRepository

class GetTopHighlightMatchesImpl(private val matchRepository: MatchRepository) :
    GetTopHighlightMatches {
    override suspend fun execute(param: GetTopHighlightMatches.Param?): List<Match> {
        return matchRepository.getTopLatestMatches(param?.top)
    }

}
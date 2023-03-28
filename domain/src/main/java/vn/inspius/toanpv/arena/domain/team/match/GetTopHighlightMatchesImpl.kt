package vn.inspius.toanpv.arena.domain.team.match

import vn.inspius.toanpv.arena.entity.Match
import vn.inspius.toanpv.arena.repository.team.match.MatchRepository

class GetTopHighlightMatchesImpl(private val matchRepository: MatchRepository) :
    GetTopHighlightMatches {
    override suspend fun execute(param: GetTopHighlightMatches.Param?): List<Match> {
        return matchRepository.getTopLatestMatches(param?.top)
    }

}
package vn.toanpv.sample.arena.domain.team.match

import vn.toanpv.sample.arena.entity.Match
import vn.toanpv.sample.arena.repository.team.match.MatchRepository

class GetMatchByTeamInteractImpl(private val matchRepository: MatchRepository) :
    GetMatchByTeamInteract {
    override suspend fun execute(param: GetMatchByTeamInteract.Param?): List<Match> {
        if (param == null)
            throw IllegalArgumentException("param GetMatchByTeamInteract.Param required")

        if (param.teamId == null)
            throw IllegalArgumentException("TeamId cannot be null")

        return param.teamId.let { teamId -> matchRepository.getAllMatchesByTeam(teamId) }
    }
}
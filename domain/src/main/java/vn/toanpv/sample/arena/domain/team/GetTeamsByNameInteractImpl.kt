package vn.toanpv.sample.arena.domain.team

import vn.toanpv.sample.arena.entity.Team
import vn.toanpv.sample.arena.repository.data.local.TeamLocalSource
import java.security.InvalidParameterException

class GetTeamsByNameInteractImpl(private val teamLocalSource: TeamLocalSource) :
    GetTeamsByNameInteract {
    override suspend fun execute(param: GetTeamsByNameInteract.Param?): List<Team> {
        if (param == null)
            throw InvalidParameterException("GetTeamsByNameInteract.Param is required")
        return teamLocalSource.getTeamsByName(param.query, param.top)
    }
}
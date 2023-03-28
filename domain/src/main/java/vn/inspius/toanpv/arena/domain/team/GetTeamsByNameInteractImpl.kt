package vn.inspius.toanpv.arena.domain.team

import vn.inspius.toanpv.arena.entity.Team
import vn.inspius.toanpv.arena.repository.data.local.TeamLocalSource
import java.security.InvalidParameterException

class GetTeamsByNameInteractImpl(private val teamLocalSource: TeamLocalSource) :
    GetTeamsByNameInteract {
    override suspend fun execute(param: GetTeamsByNameInteract.Param?): List<Team> {
        if (param == null)
            throw InvalidParameterException("GetTeamsByNameInteract.Param is required")
        return teamLocalSource.getTeamsByName(param.query, param.top)
    }
}
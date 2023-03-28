package vn.inspius.toanpv.arena.domain.team

import vn.inspius.toanpv.arena.domain.Interact
import vn.inspius.toanpv.arena.entity.Team
import vn.inspius.toanpv.arena.repository.team.TeamsRepository

class GetTeamsInteractImpl(private val teamsRepository: TeamsRepository) : GetTeamsInteract {
    override suspend fun execute(param: Interact.Param?): List<Team> {
        return teamsRepository.getTeams()
    }
}
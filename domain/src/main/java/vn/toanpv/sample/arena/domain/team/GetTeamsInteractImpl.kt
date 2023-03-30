package vn.toanpv.sample.arena.domain.team

import vn.toanpv.sample.arena.domain.Interact
import vn.toanpv.sample.arena.entity.Team
import vn.toanpv.sample.arena.repository.team.TeamsRepository

class GetTeamsInteractImpl(private val teamsRepository: TeamsRepository) : GetTeamsInteract {
    override suspend fun execute(param: Interact.Param?): List<Team> {
        return teamsRepository.getTeams()
    }
}
package vn.inspius.toanpv.arena.domain.match

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import vn.inspius.toanpv.arena.domain.team.match.GetMatchByTeamInteract
import vn.inspius.toanpv.arena.domain.team.match.GetMatchByTeamInteractImpl
import vn.inspius.toanpv.arena.domain.team.match.GetMatchesInteractImpl
import vn.inspius.toanpv.arena.repository.team.match.MatchRepository
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class MatchByTeamInteractImplTest {

    private lateinit var matchRepository: MatchRepository
    private lateinit var getMatchesInteract: GetMatchesInteractImpl
    private lateinit var getMatchByTeamInteractImpl: GetMatchByTeamInteractImpl

    @BeforeTest
    fun setUp() {
        matchRepository = mockk()
        getMatchesInteract = GetMatchesInteractImpl(matchRepository)
        getMatchByTeamInteractImpl = GetMatchByTeamInteractImpl(matchRepository)
    }

    @Test
    fun `when getAllMatchesByTeam is called with a valid teamId, verify that the repository method is called with the correct teamId`() {
        val teamId = "invalidTeamId"
        coEvery { matchRepository.getAllMatchesByTeam(teamId) } returns emptyList()

        runBlocking { getMatchByTeamInteractImpl.execute(GetMatchByTeamInteract.Param(teamId)) }

        coVerify { matchRepository.getAllMatchesByTeam(teamId) }
    }

    @Test
    fun `when getAllMatchesByTeam is called and the param is null, throw an IllegalArgumentException`() {
        try {
            runBlocking { getMatchByTeamInteractImpl.execute(null) }
        } catch (e: IllegalArgumentException) {
            assertEquals("param GetMatchByTeamInteract.Param required", e.message)
        }
    }

    @Test
    fun `when getAllMatchesByTeam is called and the teamId is null, throw an IllegalArgumentException`() {
        val teamId: String? = null

        try {
            runBlocking { getMatchByTeamInteractImpl.execute(GetMatchByTeamInteract.Param(teamId)) }
        } catch (e: IllegalArgumentException) {
            assertEquals("TeamId cannot be null", e.message)
        }
    }

    @Test
    fun `when getAllMatchesByTeam is called and the team repository returns a list of matches, verify that the use case returns the same list`() =
        runBlocking {
            val matches = TestConfig.matchBy0
            coEvery { matchRepository.getAllMatchesByTeam(TestConfig.teamId0) } returns matches

            val result =
                getMatchByTeamInteractImpl.execute(GetMatchByTeamInteract.Param(TestConfig.teamId0))

            assertEquals(matches, result)
        }

    @Test
    fun `when getAllMatchesByTeam is called and the team repository throws an exception, rethrow the exception`() {
        val teamId = "teamId"
        val exception = Exception("Something went wrong")
        coEvery { matchRepository.getAllMatchesByTeam(teamId) } throws exception

        try {
            runBlocking { getMatchByTeamInteractImpl.execute(GetMatchByTeamInteract.Param(teamId)) }
        } catch (e: Exception) {
            assertEquals(exception, e)
        }
    }
}
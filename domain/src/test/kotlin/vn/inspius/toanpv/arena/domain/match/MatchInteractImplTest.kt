package vn.inspius.toanpv.arena.domain.match

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import vn.inspius.toanpv.arena.domain.team.match.GetMatchByTeamInteractImpl
import vn.inspius.toanpv.arena.domain.team.match.GetMatchesInteractImpl
import vn.inspius.toanpv.arena.repository.team.match.MatchRepository
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class MatchInteractImplTest {

    private lateinit var repository: MatchRepository
    private lateinit var getMatchesInteract: GetMatchesInteractImpl
    private lateinit var getMatchByTeamInteractImpl: GetMatchByTeamInteractImpl

    @BeforeTest
    fun setUp() {
        repository = mockk()
        getMatchesInteract = GetMatchesInteractImpl(repository)
        getMatchByTeamInteractImpl = GetMatchByTeamInteractImpl(repository)
    }

    @Test
    fun `should return all match from repository`() = runBlocking {
        // Given
        val expectedTeams = TestConfig.matches
        coEvery { repository.getMatches() } returns expectedTeams

        // When
        val actualTeams = getMatchesInteract.execute()

        // Then
        assertEquals(actualTeams, expectedTeams)
    }

    @Test
    fun `when getMatches is called and there are no match, return empty list`() = runBlocking {
        // Given
        coEvery { repository.getMatches() } returns listOf()

        // When
        val result = getMatchesInteract.execute()

        // Then
        assertEquals(0, result.size)
    }

    @Test
    fun `when getMatches is called and there are teams, return a list with the correct size`() =
        runBlocking {
            // Given
            coEvery { repository.getMatches() } returns TestConfig.matches

            // When
            val result = getMatchesInteract.execute()

            // Then
            assertEquals(5, result.size)
        }

    @Test
    fun `when getAllTeams is called and the team repository throws an exception, rethrow the exception`() =
        runBlocking {
            // Given
            val exception = Exception("Something went wrong")
            coEvery { repository.getMatches() } throws exception

            val result = runCatching {
                getMatchesInteract.execute()
            }

            // When
//            assertThrows<Exception> {
//                useCase.execute()
//            }

            assertEquals(result.isFailure, true)
        }

}
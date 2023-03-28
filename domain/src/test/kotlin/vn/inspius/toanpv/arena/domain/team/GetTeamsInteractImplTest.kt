package vn.inspius.toanpv.arena.domain.team

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import vn.inspius.toanpv.arena.repository.team.TeamsRepository
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class GetTeamsInteractImplTest {

    private lateinit var repository: TeamsRepository
    private lateinit var useCase: GetTeamsInteractImpl

    @BeforeTest
    fun setUp() {
        repository = mockk()
        useCase = GetTeamsInteractImpl(repository)
    }

    @Test
    fun `should return all teams from repository`() = runBlocking {
        // Given
        val expectedTeams = TestConfig.teams
        coEvery { repository.getTeams() } returns expectedTeams

        // When
        val actualTeams = useCase.execute()

        // Then
        assertEquals(actualTeams, expectedTeams)
    }

    @Test
    fun `when getAllTeams is called and there are no teams, return empty list`() = runBlocking {
        // Given
        coEvery { repository.getTeams() } returns listOf()

        // When
        val result = useCase.execute()

        // Then
        assertEquals(0, result.size)
    }

    @Test
    fun `when getAllTeams is called and there are teams, return a list with the correct size`() =
        runBlocking {
            // Given
            coEvery { repository.getTeams() } returns TestConfig.teams

            // When
            val result = useCase.execute()

            // Then
            assertEquals(5, result.size)
        }

    @Test
    fun `when getAllTeams is called and the team repository throws an exception, rethrow the exception`() =
        runBlocking {
            // Given
            val exception = Exception("Something went wrong")
            coEvery { repository.getTeams() } throws exception

            val result = runCatching {
                useCase.execute()
            }

            // When
//            assertThrows<Exception> {
//                useCase.execute()
//            }

            assertEquals(result.isFailure, true)
        }
}
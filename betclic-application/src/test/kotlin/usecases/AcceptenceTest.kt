package usecases

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import ports.GameUseCase
import ports.PlayerRepository
import usecases.stubs.PlayerRespositoryInMemory
import java.util.*
import kotlin.test.Test


class AcceptenceTest {
    private lateinit var gameUseCase: GameUseCase
    private lateinit var playerRepository: PlayerRepository

    @BeforeEach
    fun setup() {
        // Setup in-memory repository
        playerRepository = PlayerRespositoryInMemory()
        gameUseCase = GameUseCaseImpl(playerRepository)
    }

    @Test
    fun `can add player to game`() {
        val pseudo = "testPlayer"
        val player = gameUseCase.addPlayerToGame(pseudo)

        assertNotNull(player)
        assertEquals(pseudo, player?.pseudo)
    }

    @Test
    fun `can get player info`() {
        val pseudo = "testPlayer"
        val addedPlayer = gameUseCase.addPlayerToGame(pseudo)

        val player = addedPlayer?.id?.let { gameUseCase.getPlayerInfo(it) }

        assertNotNull(player)
        assertEquals(pseudo, player?.pseudo)
    }

    @Test
    fun `can update player points`() {
        val pseudo = "testPlayer"
        val addedPlayer = gameUseCase.addPlayerToGame(pseudo)
        val updatedPoints = 100

        val updatedPlayer = addedPlayer?.id?.let { gameUseCase.updatePlayerPoints(it, updatedPoints) }

        assertNotNull(updatedPlayer)
        assertEquals(updatedPoints, updatedPlayer?.points)
    }

    @Test
    fun `can get game ranking`() {
        gameUseCase.addPlayerToGame("player1")
        gameUseCase.updatePlayerPoints(UUID.randomUUID(), 10) // Assuming this UUID matches the player added above
        gameUseCase.addPlayerToGame("player2")
        gameUseCase.updatePlayerPoints(UUID.randomUUID(), 20) // And this one too

        val classement = gameUseCase.getGameRank()

        assertEquals(2, classement.size)
        assertTrue(classement[0].points >= classement[1].points) // Assuming descending order by points
    }

    @Test
    fun `can finalise game`() {
        gameUseCase.addPlayerToGame("player1")
        gameUseCase.endGame()

       assertTrue(playerRepository.getALlPlayersByPointsAndRanking().isEmpty())
    }
}
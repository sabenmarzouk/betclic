package usecases


import domain.Player
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import ports.GameUseCase
import ports.PlayerRepository
import java.util.*
import kotlin.test.Test

class GameUseCaseTest {

    private lateinit var playerRepository: PlayerRepository
    private lateinit var gameUseCase: GameUseCase

    @BeforeEach
    fun setUp() {
        playerRepository = mockk(relaxed = true)
        gameUseCase = GameUseCaseImpl(playerRepository)
    }

    @Test
    fun `add player to game`() {
        val pseudo = "testPlayer"
        val player = Player(UUID.randomUUID(), pseudo, 0)

        every { playerRepository.save(any()) } returns player.id
        every { playerRepository.findPlayerById(any()) } returns player

        val result = gameUseCase.addPlayerToGame(pseudo)

        assertEquals(pseudo, result?.pseudo)
        verify(exactly = 1) { playerRepository.save(any()) }
    }

    @Test
    fun `get player info`() {
        val id = UUID.randomUUID()
        val player = Player(id, "testPlayer", 0)

        every { playerRepository.findPlayerById(id) } returns player

        val result = gameUseCase.getPlayerInfo(id)

        assertEquals(id, result?.id)
        verify(exactly = 1) { playerRepository.findPlayerById(id) }
    }

    @Test
    fun `update player points`() {
        val id = UUID.randomUUID()
        val originalPoints = 10
        val updatedPoints = 20
        val player = Player(id, "testPlayer", originalPoints)

        every { playerRepository.findPlayerById(id) } returns player
        every { playerRepository.save(any()) } answers {
            val updatedPlayer = firstArg<Player>()
            updatedPlayer.id
        }

        val result = gameUseCase.updatePlayerPoints(id, updatedPoints)

        assertEquals(updatedPoints, result?.points)
        verify(exactly = 1) { playerRepository.save(any()) }
    }

    @Test
    fun `get game classement`() {
        val players = listOf(
            Player(UUID.randomUUID(), "player1", 10),
            Player(UUID.randomUUID(), "player2", 20)
        ).sortedByDescending  { it.points }

        every { playerRepository.getALlPlayersByPointsAndRanking()} returns players

        val result = gameUseCase.getGameRank()

        assertEquals(2, result.size)
        assertEquals(20, result[0].points)
        verify(exactly = 1) { playerRepository.getALlPlayersByPointsAndRanking() }
    }

}
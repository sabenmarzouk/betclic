package infra.adapters.db

import domain.Player
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@ExtendWith(SpringExtension::class)
@DataJpaTest
@ComponentScan("infra.adapters.db")
class PlayerJpaAdapterTest {

    @Autowired
    private lateinit var playerJpaAdapter: PlayerJpaAdapter

    @Test
    fun `should save and find player by id`() {
        // Given
        val player = Player(UUID.randomUUID(), "testPlayer", 100, null)

        // When
        val savedPlayerId = playerJpaAdapter.save(player)
        val foundPlayer = playerJpaAdapter.findPlayerById(savedPlayerId)

        // Then
        assertNotNull(foundPlayer)
        assertEquals(player.pseudo, foundPlayer.pseudo)
        assertEquals(player.points, foundPlayer.points)
    }

    @Test
    fun `should delete player by id`() {
        // Given
        val player = Player(UUID.randomUUID(), "testPlayer", 100, null)
        val savedPlayerId = playerJpaAdapter.save(player)

        // When
        playerJpaAdapter.deleteById(savedPlayerId)

        // Then
        val foundPlayer = playerJpaAdapter.findPlayerById(savedPlayerId)
        assertNull(foundPlayer)
    }


    @Test
    fun `should get all players by points and ranking`() {
        // Given
        val players = listOf(
            Player(UUID.randomUUID(), "Player1", 300, 1),
            Player(UUID.randomUUID(), "Player2", 200, 2),
            Player(UUID.randomUUID(), "Player3", 100, 3)
        )
        players.forEach { playerJpaAdapter.save(it) }

        // When
        val foundPlayers = playerJpaAdapter.getALlPlayersByPointsAndRanking()

        // Then
        assertEquals(3, foundPlayers.size)
        assertEquals(players.sortedByDescending { it.points }.map { it.pseudo }, foundPlayers.map { it.pseudo })
    }
    @Test
    fun `should correctly delete a player by id`() {
        // Given
        val player = Player(UUID.randomUUID(), "testPlayer", 100, null)
        val savedId = playerJpaAdapter.save(player)

        // When
        playerJpaAdapter.deleteById(savedId)

        // Then
        val deletedPlayer = playerJpaAdapter.findPlayerById(savedId)
        assertNull(deletedPlayer, "Player should be null after deletion")
    }
    @Test
    fun `should delete all players`() {
        // Given
        listOf(
            Player(UUID.randomUUID(), "Player1", 10, 3),
            Player(UUID.randomUUID(), "Player2", 20, 2),
            Player(UUID.randomUUID(), "Player3", 30, 1)
        ).forEach { playerJpaAdapter.save(it) }

        // When
        playerJpaAdapter.deleteAll()

        // Then
        val players = playerJpaAdapter.getALlPlayersByPointsAndRanking()
        assertTrue(players.isEmpty(), "Players list should be empty after delete all")
    }
}
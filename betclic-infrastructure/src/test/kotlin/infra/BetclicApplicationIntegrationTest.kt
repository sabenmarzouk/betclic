package infra

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import domain.Player
import infra.adapters.rest.GameController
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*
import kotlin.test.assertEquals


@SpringBootTest
@ExtendWith(SpringExtension::class)
@Import(GameController::class)
@EnableAutoConfiguration
@AutoConfigureMockMvc
class BetclicApplicationIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val objectMapper: ObjectMapper = jacksonObjectMapper()
    @Test
    fun playGame(){
        // Create players and assign points
        val pseudoPoints = listOf("player1" to 200, "player2" to 400, "player3" to 100, "player4" to 300)
        val players = pseudoPoints.map { (pseudo, points) ->
            addPlayerToGame(pseudo).also { player ->
                player.id?.let { updatePoint(it, points) }
            }
        }
        // Assert the ranking based on points
        val rankedPlayers = getRank()

        // Assuming the rank is determined by the points in descending order,
        // and players[1] has the highest points and players[2] the lowest.
        assertEquals(players[1].id, rankedPlayers.first().id, "Player with highest points should rank first")
        assertEquals(players[2].id, rankedPlayers.last().id, "Player with lowest points should rank last")

        // Optionally, demonstrate or test getting player information
        players.forEach { player ->
            player.id?.let { getPlayer(it) }
        }

        // End the game as part of the cleanup
        endGame()
    }

    fun addPlayerToGame(pseudo: String): Player {
        val result = mockMvc.perform(post("/game/players")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(pseudo)))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
        return objectMapper.readValue(result.response.contentAsByteArray, Player::class.java)
    }
    fun getPlayer(playerId: UUID) {
        mockMvc.perform(get("/game/players/$playerId"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(playerId.toString()))
            .andReturn()
    }

    fun updatePoint(playerId: UUID, updatedPoints: Int) {
        mockMvc.perform(put("/game/players/$playerId/points")
            .contentType(MediaType.APPLICATION_JSON)
            .content(updatedPoints.toString()))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.points").value(updatedPoints))
    }
    fun getRank(): List<Player> {
        val results = mockMvc.perform(get("/game/players/rank"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
        return objectMapper.readValue(results.response.contentAsByteArray, object : TypeReference<List<Player>>() {})
    }
    fun endGame(){
        val stateChangeRequest = GameController.StateChangeRequest("ended")
        mockMvc.perform(put("/game/state")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(stateChangeRequest)))
            .andExpect(status().isOk)

    }

}
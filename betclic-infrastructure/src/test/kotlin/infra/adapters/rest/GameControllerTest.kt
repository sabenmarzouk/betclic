package infra.adapters.rest
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import domain.Player
import infra.config.DomainTestConfig
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ports.GameUseCase
import ports.PlayerRepository
import java.util.*

@WebMvcTest(GameController::class)
@Import(
    DomainTestConfig::class)
class GameControllerTest {
    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun playerRepository()= mockk<PlayerRepository>()
        @Bean
        fun gameUseCase() = mockk<GameUseCase>()

    }
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var gameUseCase: GameUseCase

    private val objectMapper: ObjectMapper = jacksonObjectMapper()

    @Test
    fun `Add Player - Success`() {

        val pseudo = "testPlayer"
        val player = Player(UUID.randomUUID(), pseudo, 100, 1)

        every { gameUseCase.addPlayerToGame(any()) } returns player

        mockMvc.perform(post("/game/players")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(pseudo)))
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun `Get Player Info - Success`() {
        val id = UUID.randomUUID()
        val player = Player(id, "testPlayer", 100, 1)

        every { gameUseCase.getPlayerInfo(any()) } returns player

        mockMvc.perform(get("/game/players/$id"))
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(id.toString()))
    }

    @Test
    fun `Update Player Points - Success`() {
        val id = UUID.randomUUID()
        val points = 150
        val updatedPlayer = Player(id, "testPlayer", points, 1)

        every { gameUseCase.updatePlayerPoints(id, points) } returns updatedPlayer


        mockMvc.perform(put("/game/players/{id}/points", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(points)))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.points").value(points))
    }

    @Test
    fun `Get Game Classement - Success`() {
        val players = listOf(Player(UUID.randomUUID(), "testPlayer", 100, 1))

        every { gameUseCase.getGameRank() } returns players

        mockMvc.perform(get("/game/players/rank"))
            .andExpect(status().isOk)
          .andExpect(jsonPath("$[0].pseudo").value(players[0].pseudo))
    }

    @Test
    fun `Change Game State - Success`() {
        val stateChangeRequest = GameController.StateChangeRequest("ended")

        every { gameUseCase.endGame() } just Runs // Use `just Runs` for void return types

        mockMvc.perform(put("/game/state")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"state\":\"${stateChangeRequest.state}\"}"))
            .andExpect(status().isOk)
    }
}
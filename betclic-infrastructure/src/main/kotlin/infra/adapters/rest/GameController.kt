package infra.adapters.rest

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ports.GameUseCase
import java.util.*

@RestController
@RequestMapping("/game")
class GameController(private val gameUseCase: GameUseCase) {

    @PostMapping("/players")
    fun addPlayer(@RequestBody pseudo: String): ResponseEntity<PlayerDTO> {
        val player = gameUseCase.addPlayerToGame(pseudo) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(PlayerDTO.fromPlayerDomain(player))
    }

    @GetMapping("/players/{id}")
    fun getPlayerInfo(@PathVariable id: UUID): ResponseEntity<PlayerDTO> {
        val player = gameUseCase.getPlayerInfo(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(PlayerDTO.fromPlayerDomain(player))
    }

    @PutMapping("/players/{id}/points")
    fun updatePlayerPoints(@PathVariable id: UUID, @RequestBody points: Int): ResponseEntity<PlayerDTO> {
        val updatedPlayer = gameUseCase.updatePlayerPoints(id, points) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(PlayerDTO.fromPlayerDomain(updatedPlayer))
    }

    @GetMapping("/players/rank")
    fun getGameClassement(): ResponseEntity<List<PlayerDTO>> {
        val classement = gameUseCase.getGameRank()
        return ResponseEntity.ok(classement.mapNotNull { PlayerDTO.fromPlayerDomain(it) }.toList())
    }

    @PutMapping("/state")
    fun changeGameState(@RequestBody stateChangeRequest: StateChangeRequest): ResponseEntity<Void> {
        if (stateChangeRequest.state == "ended") {
            gameUseCase.endGame()
            return ResponseEntity.ok().build()
        } else {
            return ResponseEntity.badRequest().build()
        }
    }
    data class StateChangeRequest(val state: String)
}
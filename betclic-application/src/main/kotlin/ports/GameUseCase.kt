package ports

import domain.Player
import java.util.*

interface GameUseCase {
     fun addPlayerToGame(pseudo: String): Player?
     fun getPlayerInfo(id: UUID): Player?
     fun updatePlayerPoints(id: UUID, points: Int): Player?
     fun getGameRank(): List<Player>
     fun endGame()
}

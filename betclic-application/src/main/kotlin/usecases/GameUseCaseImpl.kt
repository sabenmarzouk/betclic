package usecases

import UseCase
import domain.Player
import ports.GameUseCase
import ports.PlayerRepository
import java.util.*

@UseCase
open class GameUseCaseImpl(private val playerRepository: PlayerRepository) : GameUseCase {
    override fun addPlayerToGame(pseudo: String): Player? {
       return playerRepository.save(Player(null, pseudo, 0))?.let { playerRepository.findPlayerById(it) }
    }

    override fun getPlayerInfo(id: UUID): Player? {
        return playerRepository.findPlayerById(id)
    }

    override fun updatePlayerPoints(id: UUID, points: Int): Player? {
        val player = getPlayerInfo(id)?.updatePoints(points)
        return playerRepository.save(player)?.let { playerRepository.findPlayerById(it) }
    }
    override fun getGameRank(): List<Player> {
        return playerRepository.getALlPlayersByPointsAndRanking()
    }

    override fun endGame() {
         playerRepository.deleteAll()
    }
}
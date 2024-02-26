package ports

import domain.Player
import java.util.*


interface PlayerRepository {
    fun findPlayerById(playerId: UUID?): Player?
    fun save(player: Player?): UUID?
    fun deleteById(playerId: UUID?)
    fun deleteAll()
    fun getALlPlayersByPointsAndRanking(): List<Player>
}
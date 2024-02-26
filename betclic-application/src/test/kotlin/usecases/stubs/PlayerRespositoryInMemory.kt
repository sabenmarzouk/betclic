package usecases.stubs

import domain.Player
import ports.PlayerRepository
import java.util.*


class PlayerRespositoryInMemory: PlayerRepository {
    private var entities: MutableMap<UUID, Player> = mutableMapOf<UUID, Player>()
    override fun findPlayerById(playerId: UUID?): Player? {
        return entities[playerId]
    }

    override fun save(player: Player?): UUID? {
        player ?: return null

        val newPlayer = Player(id = player.id ?: UUID.randomUUID(), player.pseudo, player.points, player.classement)

        entities[newPlayer.id!!] = newPlayer
        return newPlayer.id
    }

    override fun deleteById(playerId: UUID?) {
        entities.remove(playerId)
    }

    override fun deleteAll() {
        entities = mutableMapOf<UUID, Player>()
    }

    override fun getALlPlayersByPointsAndRanking(): List<Player> {
        return entities.values.sortedBy { it.points }.toList()
    }
}
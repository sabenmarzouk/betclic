package infra.adapters.db

import domain.Player
import org.springframework.stereotype.Component
import ports.PlayerRepository
import java.util.*

@Component
class PlayerJpaAdapter(private val playerJPARepository: PlayerJPARepository): PlayerRepository {
    override fun findPlayerById(playerId: UUID?): Player? {
        return playerId?.let { playerJPARepository.findPlayerWithRankById(playerId)?.toDomain() }
    }


    override fun save(player: Player?): UUID? {
        return player?.let { playerJPARepository.save(PlayerEntity.fromDomain(it)).id}.also { playerJPARepository.flush()  }
          }

    override fun deleteById(playerId: UUID?) {
       playerId?.let { playerJPARepository.deleteById(playerId)}
    }

    override fun deleteAll() {
       playerJPARepository.deleteAll()
    }

    override fun getALlPlayersByPointsAndRanking(): List<Player> {
        return playerJPARepository.findAllByRank().map { it.toDomain() }.toList()
    }
}
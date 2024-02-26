package infra.adapters.rest

import domain.Player
import java.util.*

data class PlayerDTO(
    val id: UUID? = null,
    val pseudo: String,
    val points: Int,
    val classement: Int? = null
){

    companion object {
        fun fromPlayerDomain(player: Player?): PlayerDTO? {
                return player?.let { PlayerDTO(it.id, it.pseudo, it.points, it.classement)}
        }

    }
}

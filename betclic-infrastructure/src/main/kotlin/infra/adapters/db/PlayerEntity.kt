package infra.adapters.db

import domain.Player
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.util.*

@Entity
class PlayerEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    val id: UUID? = null,

    @Column(nullable = false)
    var pseudo: String,

    @Column(nullable = false)
    var points: Int,

    var classement: Int? = null,

    ) {
    fun toDomain(): Player {
        return Player(id, pseudo, points, classement)
    }
    companion object {
        fun fromDomain(player: Player) : PlayerEntity {
            return PlayerEntity(player.id, player.pseudo, player.points, player.classement)
        }
    }

}
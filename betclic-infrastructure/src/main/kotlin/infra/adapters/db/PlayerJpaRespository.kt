package infra.adapters.db

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*


interface PlayerJPARepository : JpaRepository<PlayerEntity, UUID> {
    @Query(value = " SELECT p.id, p.pseudo, p.points, \n" +
            "          RANK() OVER (ORDER BY p.points DESC) as classement\n" +
            "   FROM player_entity p\n" +
            "   WHERE p.id = :id", nativeQuery = true)
    fun findPlayerWithRankById(@Param("id") id: UUID): PlayerEntity?
    @Query("SELECT p.id as id, p.pseudo as pseudo, p.points as points, RANK() OVER (ORDER BY p.points DESC) as classement FROM player_entity p ORDER BY p.points DESC", nativeQuery = true)
    fun findAllByRank(): List<PlayerEntity>
}
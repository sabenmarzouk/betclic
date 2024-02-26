package domain

import BusinessException
import java.util.*

class Player(val id: UUID?, var pseudo: String, var points: Int, var classement: Int?){
    constructor(id: UUID?, pseudo: String, points: Int) : this(id, pseudo, points, null)

    init {
        validatePoints(points)
        validatePseudo(pseudo)
    }
    fun updatePoints(points: Int): Player {
        validatePoints(points)
        this.points = points
        return this;
    }
    private fun validatePoints(points: Int) {
        if (points < 0)
            throw BusinessException("Quantity must be greater than zero")
    }
    private fun validatePseudo(pseudo: String) {
        if (pseudo.isEmpty())
            throw BusinessException("Pseudo can't be empty")
    }
}
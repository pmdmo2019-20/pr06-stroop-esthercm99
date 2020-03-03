package es.iessaladillo.pedrojoya.stroop.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

data class GamePlayer (
    @Embedded
    val player: Player,
    @Relation(
        parentColumn = "idUser",
        entityColumn = "playerId"
    )
    val game: Game
)
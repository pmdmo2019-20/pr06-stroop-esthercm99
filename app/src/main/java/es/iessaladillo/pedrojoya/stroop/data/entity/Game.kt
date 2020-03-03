package es.iessaladillo.pedrojoya.stroop.data.entity

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "game")
data class Game(
    @PrimaryKey(autoGenerate = true)
    val gameId: Int,
    var playerId: Int,
    var modeGame: String,
    var time: Int,
    var numWords: Int,
    var numCorrects: Int
)
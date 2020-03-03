package es.iessaladillo.pedrojoya.stroop.data

import androidx.lifecycle.LiveData
import androidx.room.*
import es.iessaladillo.pedrojoya.stroop.data.entity.Game
import es.iessaladillo.pedrojoya.stroop.data.entity.GamePlayer

@Dao
interface GameDao {
    @Insert
    fun insertGame(game: Game)

    @Query("SELECT * FROM game")
    fun queryAllGame(): LiveData<List<Game>>

    @Query("SELECT * FROM game WHERE gameId = :gameId")
    fun queryGameById(gameId: Int): Game
}
package es.iessaladillo.pedrojoya.stroop.data

import androidx.lifecycle.LiveData
import androidx.room.*
import es.iessaladillo.pedrojoya.stroop.data.entity.Player

@Dao
interface PlayerDao {
    @Insert
    fun insertPlayer(player: Player)

    @Update
    fun updateUser(player: Player)

    @Delete
    fun deleteUser(player: Player)

    @Query("SELECT * FROM Player WHERE idUser = :idUser")
    fun getUser(idUser: Long) : Player

    @Query("SELECT * FROM Player")
    fun getAllUsers(): LiveData<List<Player>>
}
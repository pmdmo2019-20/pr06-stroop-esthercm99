package es.iessaladillo.pedrojoya.stroop.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.iessaladillo.pedrojoya.stroop.data.entity.Game
import es.iessaladillo.pedrojoya.stroop.data.entity.Player

@Database(
    entities = [Player::class, Game::class],
    version = 1,
    exportSchema = true
)
abstract class DatabasePlayer : RoomDatabase() {

    abstract val playerDao: PlayerDao
    abstract val gameDao: GameDao

    companion object {

        @Volatile
        private var INSTANCE: DatabasePlayer? = null

        fun getInstance(context: Context): DatabasePlayer {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            DatabasePlayer::class.java,
                            "database_player"
                        ).build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}
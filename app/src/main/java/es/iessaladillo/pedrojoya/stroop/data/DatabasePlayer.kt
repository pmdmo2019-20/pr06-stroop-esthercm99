package es.iessaladillo.pedrojoya.stroop.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.data.entity.Player
import kotlin.concurrent.thread

@Database(
    entities = [Player::class],
    version = 1,
    exportSchema = true
)
abstract class DatabasePlayer : RoomDatabase() {

    abstract val playerDao: PlayerDao

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
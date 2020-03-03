package es.iessaladillo.pedrojoya.stroop.ui.result

import android.app.Application
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.observe
import androidx.preference.PreferenceManager
import es.iessaladillo.pedrojoya.stroop.data.GameDao
import es.iessaladillo.pedrojoya.stroop.data.PlayerDao
import es.iessaladillo.pedrojoya.stroop.data.entity.Game
import es.iessaladillo.pedrojoya.stroop.data.entity.Player
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class ResultFragmentViewmodel(private val playerDao: PlayerDao, private val gameDao: GameDao, private val application: Application) : ViewModel() {

    lateinit var listGame: LiveData<List<Game>>

    var game: MutableLiveData<Game> = MutableLiveData()
    var player: MutableLiveData<Player> = MutableLiveData()

    fun setGame() {
        listGame = gameDao.queryAllGame()
    }
    fun setPlayer(id: Long) {
        thread {
            player.postValue(playerDao.getUser(id))
        }

    }
}
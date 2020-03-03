package es.iessaladillo.pedrojoya.stroop.ui.player.playerEdition

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.*
import androidx.preference.PreferenceManager
import es.iessaladillo.pedrojoya.stroop.base.Event
import es.iessaladillo.pedrojoya.stroop.data.PlayerDao
import es.iessaladillo.pedrojoya.stroop.data.entity.Player
import kotlin.concurrent.thread

class PlayerEditionFragmentViewmodel(private val playerDao: PlayerDao, private val application: Application) : ViewModel() {

    private val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(application)
    }

    val players: LiveData<List<Player>> = playerDao.getAllUsers()

    private val _currentIdPlayer: MutableLiveData<Long> = MutableLiveData()
    val currentUserId: LiveData<Long>
        get() = _currentIdPlayer

    private val _currentPosition : MutableLiveData<Long> = MutableLiveData(0)
    val currentPosition : LiveData<Long>
        get() = _currentPosition

    init {
        _currentIdPlayer.value = settings.getLong("currentIdPlayer", -1)
    }

    fun refresh() {
        _currentIdPlayer.value = settings.getLong("currentIdPlayer", -1)
    }

    fun setCurrentPosition(position: Long){
        _currentPosition.value = position
    }

    fun updatePlayer(id: Long, name: String, avatar: Long) {
        thread {
            playerDao.updateUser(Player(id, name, avatar))
            Event(true)
        }
    }

    fun deletePlayer(id: Long, name: String, avatar: Long) {
        thread {
            playerDao.deleteUser(Player(id, name, avatar))
            Event(true)
        }
    }

}
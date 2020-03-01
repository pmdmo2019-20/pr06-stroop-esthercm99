package es.iessaladillo.pedrojoya.stroop.ui.player.playerCreation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.stroop.data.PlayerDao
import es.iessaladillo.pedrojoya.stroop.data.entity.Player
import kotlin.concurrent.thread

class PlayerCreationFragmentViewmodel(private val playerDao: PlayerDao, private val application: Application) : ViewModel() {

    private val _currentPosition : MutableLiveData<Long> = MutableLiveData(0)
    val currentPosition : LiveData<Long>
        get() = _currentPosition

    fun setCurrentPosition(position: Long){
        _currentPosition.value = position
    }

    fun insertPlayer(player: Player) {
        thread {
            playerDao.insertPlayer(player)
        }
    }

}
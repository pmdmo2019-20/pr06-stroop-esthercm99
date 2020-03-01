package es.iessaladillo.pedrojoya.stroop.ui.player.playerSelection

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.stroop.data.PlayerDao
import es.iessaladillo.pedrojoya.stroop.data.entity.Player

class PlayerSelectionFragmentViewmodel(private val playerDao: PlayerDao, private val application: Application) : ViewModel() {

    val users: LiveData<List<Player>> = playerDao.getAllUsers()

    private val _currentPlayerAvatar :MutableLiveData<Long> = MutableLiveData()
    val currentAvatarPlayer :LiveData<Long>
        get() = _currentPlayerAvatar

    private val _currentPlayer :MutableLiveData<String> = MutableLiveData()
    val currentUsernamePlayer :LiveData<String>
        get()=_currentPlayer

    fun setCurrentAvatar(avatarId: Long){
        _currentPlayerAvatar.value = avatarId
    }
    fun setCurrentPlayer(player: String){
        _currentPlayer.value = player
    }

}
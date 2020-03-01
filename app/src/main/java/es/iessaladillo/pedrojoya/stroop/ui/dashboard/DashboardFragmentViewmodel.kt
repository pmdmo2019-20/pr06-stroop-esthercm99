package es.iessaladillo.pedrojoya.stroop.ui.dashboard

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import es.iessaladillo.pedrojoya.stroop.data.PlayerDao
import es.iessaladillo.pedrojoya.stroop.data.entity.Player

class DashboardFragmentViewmodel (playerDao: PlayerDao, private val application: Application) : ViewModel() {
    private val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(application)
    }

    val players: LiveData<List<Player>> = playerDao.getAllUsers()

    private val _currentIdPlayer: MutableLiveData<Long> = MutableLiveData()
    val currentUserId: LiveData<Long>
        get() = _currentIdPlayer

    init {
        _currentIdPlayer.value = settings.getLong("currentIdPlayer", -1)
    }

    fun refresh() {
        _currentIdPlayer.value = settings.getLong("currentIdPlayer", -1)
    }
}
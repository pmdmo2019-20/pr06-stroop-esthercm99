package es.iessaladillo.pedrojoya.stroop.ui.player.playerSelection

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.stroop.data.PlayerDao

class PlayerSelectionFragmentViewmodelFactory (private val playerDao: PlayerDao, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = PlayerSelectionFragmentViewmodel(playerDao, application) as T
}
package es.iessaladillo.pedrojoya.stroop.ui.player.playerCreation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.stroop.data.PlayerDao
import es.iessaladillo.pedrojoya.stroop.ui.player.playerSelection.PlayerSelectionFragmentViewmodel

class PlayerCreationFragmentViewmodelFactory (private val playerDao: PlayerDao, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = PlayerCreationFragmentViewmodel(playerDao, application) as T
}
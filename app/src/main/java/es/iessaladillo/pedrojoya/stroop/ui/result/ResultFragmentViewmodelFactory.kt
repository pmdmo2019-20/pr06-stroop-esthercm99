package es.iessaladillo.pedrojoya.stroop.ui.result

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.stroop.data.GameDao
import es.iessaladillo.pedrojoya.stroop.data.PlayerDao

class ResultFragmentViewmodelFactory (private val playerDao: PlayerDao, private val gameDao: GameDao, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = ResultFragmentViewmodel(playerDao, gameDao, application) as T
}
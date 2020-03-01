package es.iessaladillo.pedrojoya.stroop.ui.dashboard

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.stroop.data.PlayerDao

class DashboardFragmentViewmodelFactory (private val playerDao: PlayerDao, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = DashboardFragmentViewmodel(playerDao, application) as T
}
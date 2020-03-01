package es.iessaladillo.pedrojoya.stroop.ui.player.playerCreation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayerCreationFragmentViewmodel() : ViewModel() {

    private val _currentPosition : MutableLiveData<Long> = MutableLiveData(0)
    val currentPosition : LiveData<Long>
        get() = _currentPosition

    fun setCurrentPosition(position: Long){
        _currentPosition.value = position
    }
}
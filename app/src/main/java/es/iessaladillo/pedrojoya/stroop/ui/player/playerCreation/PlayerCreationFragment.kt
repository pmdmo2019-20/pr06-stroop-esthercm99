package es.iessaladillo.pedrojoya.stroop.ui.player.playerCreation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.iessaladillo.pedrojoya.stroop.R

class PlayerCreationFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_player_creation, container, false)
    }

    companion object {
        fun newInstance() =
            PlayerCreationFragment()
    }
}

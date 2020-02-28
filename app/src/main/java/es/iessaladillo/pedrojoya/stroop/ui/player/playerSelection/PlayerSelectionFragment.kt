package es.iessaladillo.pedrojoya.stroop.ui.player.playerSelection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import kotlinx.android.synthetic.main.fragment_player_selection.*
import kotlinx.android.synthetic.main.main_activity.*

class PlayerSelectionFragment : Fragment() {

    private val navController: NavController by lazy {
        NavHostFragment.findNavController(navHostFragment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_player_selection, container, false)
    }

    companion object {
        fun newInstance() =
            PlayerSelectionFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupToolbar()
        lblEmptyView.setOnClickListener { navigateToCreatePlayer() }
    }

    private fun navigateToCreatePlayer() {
        navController.navigate(R.id.playerCreationFragment)
    }

    private fun setupToolbar() {
        (requireActivity() as OnToolbarAvailableListener).onToolbarCreated(toolbarSelection)
    }
}

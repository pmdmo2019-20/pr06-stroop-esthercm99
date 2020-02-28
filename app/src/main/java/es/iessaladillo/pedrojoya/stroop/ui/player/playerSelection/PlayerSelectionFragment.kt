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
import es.iessaladillo.pedrojoya.stroop.ui.dialogInfo.DialogInfoFragment
import kotlinx.android.synthetic.main.dashboard_fragment.*
import kotlinx.android.synthetic.main.fragment_player_selection.*
import kotlinx.android.synthetic.main.main_activity.*

class PlayerSelectionFragment : Fragment() {

    private val navController: NavController by lazy {
        NavHostFragment.findNavController(navHostFragment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_player_selection, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupToolbar()

        fabAddUser.setOnClickListener { navigateToCreatePlayer() }
        iconEmptyView.setOnClickListener { navigateToCreatePlayer() }
        lblEmptyView.setOnClickListener { navigateToCreatePlayer() }
    }

    private fun navigateToCreatePlayer() {
        navController.navigate(R.id.playerCreationFragment)
    }

    private fun setupToolbar() {
        (requireActivity() as OnToolbarAvailableListener).onToolbarCreated(toolbarSelection)
        toolbarSelection.inflateMenu(R.menu.menu_info)
        toolbarSelection.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.btnInfoMenu -> DialogInfoFragment.newInstance(
                    getString(R.string.player_selection_help_description)
                )
                    .show(requireFragmentManager(), "InfoDialog")
            }
            true
        }
    }
}

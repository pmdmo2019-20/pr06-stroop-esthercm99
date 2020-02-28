package es.iessaladillo.pedrojoya.stroop.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.ui.dialogInfo.DialogInfoFragment
import kotlinx.android.synthetic.main.dashboard_fragment.*
import kotlinx.android.synthetic.main.main_activity.*


class DashboardFragment : Fragment() {

    private val navController: NavController by lazy {
        findNavController(navHostFragment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dashboard_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        infoPlayerDashboard.setOnClickListener { navigateToPlayerSelection() }
        btn_dash_setting.setOnClickListener { navigateToSettings() }
        btn_dash_assistant.setOnClickListener { navigateToAssistant() }
        btn_dash_player.setOnClickListener { navigateToPlayerSelection() }
        btn_dash_about.setOnClickListener { navigateToAbout() }
    }

    private fun setupToolbar() {
        (requireActivity() as OnToolbarAvailableListener).onToolbarCreated(toolbar)
        toolbar.inflateMenu(R.menu.menu_info)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.btnInfoMenu -> DialogInfoFragment.newInstance(
                    getString(R.string.dashboard_help_description)
                )
                    .show(requireFragmentManager(), "InfoDialog")
            }
            true
        }
    }

    private fun navigateToSettings() {
        navController.navigate(R.id.settingsFragment)
    }
    private fun navigateToAssistant() {
        navController.navigate(R.id.assistantFragment)
    }
    private fun navigateToPlayerSelection() {
        navController.navigate(R.id.playerSelectionFragment)
    }
    private fun navigateToAbout() {
        navController.navigate(R.id.aboutFragment)
    }

}

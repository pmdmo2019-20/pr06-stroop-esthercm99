package es.iessaladillo.pedrojoya.stroop.ui.dashboard

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.preference.PreferenceManager
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.data.DatabasePlayer
import es.iessaladillo.pedrojoya.stroop.data.entity.Player
import es.iessaladillo.pedrojoya.stroop.ui.dialogInfo.DialogInfoFragment
import kotlinx.android.synthetic.main.dashboard_fragment.*
import kotlinx.android.synthetic.main.main_activity.*


class DashboardFragment : Fragment() {

    private val navController: NavController by lazy {
        findNavController(navHostFragment)
    }
    private val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(activity)
    }
    private val viewmodel: DashboardFragmentViewmodel by viewModels {
        DashboardFragmentViewmodelFactory(DatabasePlayer.getInstance(this.requireContext()).playerDao, requireActivity().application)
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

        setupShowPlayer()
        navigations()
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
    private fun setupShowPlayer() {

        viewmodel.refresh()

        if (settings.getLong("currentIdPlayer", -1) != -1L) {
            var positionPlayer: Int = -1

            viewmodel.currentUserId.observe(this) {  position ->
                positionPlayer = position.toInt()
            }

            viewmodel.players.observe(this) { listPlayers ->
                val user: Player = listPlayers[positionPlayer-1]
                imgPlayerDash.setImageResource(user.image.toInt())
                namePlayerDash.text = user.username
            }
        } else {
            imgPlayerDash.setImageResource(R.drawable.logo)
            namePlayerDash.text = getString(R.string.player_selection_no_player_selected)
        }

    }
    private fun navigations() {
        infoPlayerDashboard.setOnClickListener { navigateToPlayerSelection() }
        btn_dash_setting.setOnClickListener { navigateToSettings() }
        btn_dash_assistant.setOnClickListener { navigateToAssistant() }
        btn_dash_player.setOnClickListener { navigateToPlayerSelection() }
        btn_dash_about.setOnClickListener { navigateToAbout() }
    }

    private fun navigateToAbout() = navController.navigate(R.id.aboutFragment)
    private fun navigateToSettings() = navController.navigate(R.id.settingsFragment)
    private fun navigateToAssistant() = navController.navigate(R.id.assistantFragment)
    private fun navigateToPlayerSelection() = navController.navigate(R.id.playerSelectionFragment)
}

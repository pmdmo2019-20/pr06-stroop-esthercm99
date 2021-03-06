package es.iessaladillo.pedrojoya.stroop.ui.player.playerSelection

import android.content.SharedPreferences
import android.opengl.Visibility
import android.os.Bundle
import android.provider.Settings.Global.putLong
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.data.DatabasePlayer
import es.iessaladillo.pedrojoya.stroop.data.entity.Player
import es.iessaladillo.pedrojoya.stroop.ui.dialogInfo.DialogInfoFragment
import kotlinx.android.synthetic.main.dashboard_fragment.*
import kotlinx.android.synthetic.main.fragment_player_selection.*
import kotlinx.android.synthetic.main.main_activity.*

class PlayerSelectionFragment : Fragment() {

    private val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(activity)
    }
    private lateinit var playerSelectionAdapter: PlayerSelectionFragmentAdapter
    private val navController: NavController by lazy { NavHostFragment.findNavController(navHostFragment) }
    private val viewmodel: PlayerSelectionFragmentViewmodel by viewModels {
        PlayerSelectionFragmentViewmodelFactory(DatabasePlayer.getInstance(this.requireContext()).playerDao, requireActivity().application)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_player_selection, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observe()
        setupToolbar()
        setupAdapter()
        setupRecyclerView()
        navigations()
        observe()
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
    private fun setupAdapter() {
        playerSelectionAdapter = PlayerSelectionFragmentAdapter().also {
            it.onItemClickListener = { position -> playerSelection(position) }
        }
    }
    private fun setupRecyclerView() {
        lstPlayers.run {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity, 2)
            adapter = playerSelectionAdapter
        }
    }

    private fun observe() {

        viewmodel.users.observe(this) {
            showPlayers(it)
        }

        viewmodel.currentAvatarPlayer.observe(this){
            if (it == 0L) {
                imgPlayerSelect.setImageResource(R.drawable.logo)
                btnTextEdit.visibility = View.INVISIBLE
                btnEdit.visibility = View.INVISIBLE
            } else {
                imgPlayerSelect.setImageResource(it.toInt())
                btnTextEdit.visibility = View.VISIBLE
                btnEdit.visibility = View.VISIBLE
            }
        }

        viewmodel.currentUsernamePlayer.observe(this) {
            if (it.isNotEmpty()) {
                namePlayerSelect.text = it
            }
        }

    }
    private fun showPlayers(playerList: List<Player>) {
        lstPlayers.post {
            playerSelectionAdapter.submitList(playerList)
            iconEmptyView.visibility = if (playerList.isEmpty()) View.VISIBLE else View.INVISIBLE
            lblEmptyView.visibility = if (playerList.isEmpty()) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun playerSelection(position: Int) {
        settings.edit {
            putLong("currentIdPlayer", playerSelectionAdapter.dataList[position].idUser)
        }
        viewmodel.setCurrentAvatar(playerSelectionAdapter.dataList[position].image)
        viewmodel.setCurrentPlayer(playerSelectionAdapter.dataList[position].username)
    }

    private fun navigations() {
        btnEdit.setOnClickListener { navigateToEditPlayer() }
        btnTextEdit.setOnClickListener { navigateToEditPlayer() }
        fabAddUser.setOnClickListener { navigateToCreatePlayer() }
        iconEmptyView.setOnClickListener { navigateToCreatePlayer() }
        lblEmptyView.setOnClickListener { navigateToCreatePlayer() }
    }

    private fun navigateToEditPlayer() = navController.navigate(R.id.playerEditionFragment)
    private fun navigateToCreatePlayer() = navController.navigate(R.id.playerCreationFragment)
}

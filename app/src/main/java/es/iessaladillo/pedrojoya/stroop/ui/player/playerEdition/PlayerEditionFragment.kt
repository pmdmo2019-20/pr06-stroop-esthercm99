package es.iessaladillo.pedrojoya.stroop.ui.player.playerEdition

import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.avatars
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.data.DatabasePlayer
import es.iessaladillo.pedrojoya.stroop.data.entity.Player
import es.iessaladillo.pedrojoya.stroop.extensions.hideSoftKeyboard
import es.iessaladillo.pedrojoya.stroop.ui.dialogInfo.DialogInfoFragment
import kotlinx.android.synthetic.main.fragment_player_edition.*
import kotlinx.android.synthetic.main.main_activity.*

class PlayerEditionFragment : Fragment() {

    private val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(activity)
    }
    private lateinit var playerCreationAdapter: PlayerEditionFragmentAdapter
    private val navController: NavController by lazy { NavHostFragment.findNavController(navHostFragment) }
    private val viewmodel: PlayerEditionFragmentViewmodel by viewModels {
        PlayerEditionFragmentViewmodelFactory(DatabasePlayer.getInstance(this.requireContext()).playerDao, requireActivity().application)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_player_edition, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupToolbar()
        setupAdapter()
        setupRecyclerView()
        setupShowPlayer()
        fabSaveUser.setOnClickListener { updatePlayer() }
        observe()
    }

    private fun setupToolbar() {
        (requireActivity() as OnToolbarAvailableListener).onToolbarCreated(toolbarEdition)
        toolbarEdition.inflateMenu(R.menu.menu_delete)
        toolbarEdition.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.btnDeleteMenu -> showDeleteDialog()
                R.id.btnInfoMenu -> DialogInfoFragment.newInstance(getString(R.string.player_edition_help_description)).show(requireFragmentManager(), "InfoDialog")
            }
            true
        }
    }
    private fun setupAdapter() {
        playerCreationAdapter = PlayerEditionFragmentAdapter().also {
            it.onItemClickListener = { position -> avatarSelection(position) }
        }
    }
    private fun setupRecyclerView() {
        lstAvatar.run {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity, 3)
            adapter = playerCreationAdapter
        }
    }

    private fun observe() {
        viewmodel.currentPosition.observe(this){
            if (it == 0L) {
                imgPlayerEdition.setImageResource(R.drawable.logo)
            } else {
                imgPlayerEdition.setImageResource(it.toInt())
            }
        }
    }

    private fun setupShowPlayer() {
        viewmodel.refresh()

        if (settings.getLong("currentIdPlayer", -1) != -1L) {
            var positionPlayer: Long = -1

            viewmodel.currentUserId.observe(this) {  position ->
                positionPlayer = position
            }

            viewmodel.players.observe(this) { listPlayers ->
                listPlayers.forEach {
                    if (it.idUser == positionPlayer) {
                        avatarSelection(positionPlayer.toInt())
                        imgPlayerEdition.setImageResource(it.image.toInt())
                        txtPlayerEdition.setText(it.username)
                    }
                }
            }

        } else {
            imgPlayerEdition.setImageResource(R.drawable.logo)
        }

    }

    private fun avatarSelection(position: Int) = viewmodel.setCurrentPosition(avatars[position].toLong())
    private fun updatePlayer() {
        if (txtPlayerEdition.text.toString().isNotEmpty() && viewmodel.currentPosition.value != 0.toLong()) {
            txtPlayerEdition.hideSoftKeyboard()
            viewmodel.currentUserId.observe(this) {
                viewmodel.updatePlayer(it, txtPlayerEdition.text.toString(), viewmodel.currentPosition.value!!.toLong())
                settings.edit {
                    putLong("currentIdPlayer", it)
                }
            }
            navController.navigateUp()
        }
    }
    private fun showDeleteDialog() {
        AlertDialog.Builder(context).setTitle(R.string.player_deletion_title)
            .setMessage(R.string.player_deletion_message)
            .setCancelable(false)
            .setPositiveButton("YES") { _, _ -> deletePlayer() }
            .setNegativeButton("NO"){ _, _ -> }                     // No hace nada
            .show()
    }
    private fun deletePlayer() {
        viewmodel.currentUserId.observe(this) {
            viewmodel.deletePlayer(it, txtPlayerEdition.text.toString(), viewmodel.currentPosition.value!!.toLong())
            settings.edit {
                putLong("currentIdPlayer", -1)
            }
        }
        navController.navigateUp()
    }

}

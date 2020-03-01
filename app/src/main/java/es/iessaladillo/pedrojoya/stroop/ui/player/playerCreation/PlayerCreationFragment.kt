package es.iessaladillo.pedrojoya.stroop.ui.player.playerCreation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.avatars
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.data.DatabasePlayer
import es.iessaladillo.pedrojoya.stroop.data.entity.Player
import es.iessaladillo.pedrojoya.stroop.extensions.hideSoftKeyboard
import es.iessaladillo.pedrojoya.stroop.ui.dialogInfo.DialogInfoFragment
import kotlinx.android.synthetic.main.fragment_player_creation.*
import kotlinx.android.synthetic.main.main_activity.*

class PlayerCreationFragment : Fragment() {

    private lateinit var playerCreationAdapter: PlayerCreationFragmentAdapter
    private val navController: NavController by lazy { NavHostFragment.findNavController(navHostFragment) }
    private val viewmodel: PlayerCreationFragmentViewmodel by viewModels {
        PlayerCreationFragmentViewmodelFactory(DatabasePlayer.getInstance(this.requireContext()).playerDao, requireActivity().application)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_player_creation, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupToolbar()
        setupAdapter()
        setupRecyclerView()
        fabSaveUser.setOnClickListener { savePlayer() }
        observe()
    }

    private fun setupToolbar() {
        (requireActivity() as OnToolbarAvailableListener).onToolbarCreated(toolbarCreation)
        toolbarCreation.inflateMenu(R.menu.menu_info)
        toolbarCreation.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.btnInfoMenu -> DialogInfoFragment.newInstance(getString(R.string.player_creation_help_description)).show(requireFragmentManager(), "InfoDialog")
            }
            true
        }
    }
    private fun setupAdapter() {
        playerCreationAdapter = PlayerCreationFragmentAdapter().also {
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
                imgPlayerCreation.setImageResource(R.drawable.logo)
            } else {
                imgPlayerCreation.setImageResource(it.toInt())
            }
        }
    }

    private fun avatarSelection(position: Int) = viewmodel.setCurrentPosition(avatars[position].toLong())
    private fun savePlayer() {
        if (txtPlayerCreation.text.toString().isNotEmpty() && viewmodel.currentPosition.value != 0.toLong()) {
            txtPlayerCreation.hideSoftKeyboard()
            viewmodel.insertPlayer(Player(0, txtPlayerCreation.text.toString(), viewmodel.currentPosition.value!!.toLong()))
            Toast.makeText(context, "Save", Toast.LENGTH_SHORT).show()
            navController.navigateUp()
        }
    }

}

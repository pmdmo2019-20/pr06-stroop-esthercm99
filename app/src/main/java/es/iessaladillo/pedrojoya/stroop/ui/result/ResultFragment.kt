package es.iessaladillo.pedrojoya.stroop.ui.result

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.PreferenceManager
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.data.DatabasePlayer
import es.iessaladillo.pedrojoya.stroop.data.entity.Game
import es.iessaladillo.pedrojoya.stroop.data.entity.GamePlayer
import es.iessaladillo.pedrojoya.stroop.data.entity.Player
import kotlinx.android.synthetic.main.fragment_result.*
import kotlinx.android.synthetic.main.main_activity.*
import kotlin.concurrent.thread

class ResultFragment : Fragment() {

    private val navController: NavController by lazy {
        NavHostFragment.findNavController(navHostFragment)
    }
    private val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(activity)
    }
    private val viewModel: ResultFragmentViewmodel by viewModels {
        ResultFragmentViewmodelFactory(DatabasePlayer.getInstance(this.requireContext()).playerDao, DatabasePlayer.getInstance(this.requireContext()).gameDao, requireActivity().application)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_result, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupToolbar()
        showResult()
    }
    private fun setupToolbar() = (requireActivity() as OnToolbarAvailableListener).onToolbarCreated(toolbarResult)

    private fun showResult() {

        viewModel.run {
            setGame()

            listGame.observe(viewLifecycleOwner) {
                // val idGame: Int = settings.getInt("currentGame", 0)
                var games : Game?= null
                for (game in it) {
                    if (game.gameId == it.size-1) {
                       games = game
                    }
                }

                if (games != null) {
                    viewModel.setPlayer(games.playerId.toLong())
                    player.observe(viewLifecycleOwner) { player ->
                        imgPlayer.setImageResource(player.image.toInt())
                        namePlayer.text = player.username
                    }

                    viewModel.game.value = games

                    game.observe(viewLifecycleOwner) { gameObs ->
                        numCorrect.text = (gameObs.numCorrects).toString()
                        numIncorrect.text = (gameObs.numWords - gameObs.numCorrects).toString()
                        points.text = (gameObs.numCorrects * 10).toString()
                    }
                }

            }
        }
    }


}

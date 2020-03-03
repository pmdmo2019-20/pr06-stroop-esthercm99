package es.iessaladillo.pedrojoya.stroop.ui.game

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.PreferenceManager
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.data.DatabasePlayer
import es.iessaladillo.pedrojoya.stroop.data.entity.Game
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.main_activity.*
import kotlin.concurrent.thread

class GameFragment : Fragment() {
    private val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(activity)
    }
    private val viewModel: GameViewModel by viewModels {
        GameFragmentViewmodelFactory(DatabasePlayer.getInstance(this.requireContext()).playerDao, DatabasePlayer.getInstance(this.requireContext()).gameDao, requireActivity().application)
    }
    private val navController: NavController by lazy { NavHostFragment.findNavController(navHostFragment) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_game, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        startGame()
        inGame()
        observeAnswer()
    }

    private fun startGame() {
        settingGame()
        val wordPerTime = settings.getString(getString(R.string.prefWordTime_key), getString(R.string.prefWordTime_value_1000))!!.toInt()
        viewModel.startGameThread(viewModel.time, wordPerTime)
    }
    private fun settingGame() {
        val allPlayers = DatabasePlayer.getInstance(requireContext()).playerDao.getAllUsers()

        allPlayers.observe(viewLifecycleOwner) {list ->
            list.forEach { player ->
                if (player.idUser == settings.getLong("currentIdPlayer", -1)) {
                    viewModel.player = player
                }
            }
        }

        viewModel.time = settings.getString(getString(R.string.prefGameTime_key), getString(R.string.prefGameTime_value_60))!!.toInt()
        progressBarGame.max = viewModel.time
        progressBarGame.progress = viewModel.time

        viewModel.attempts.value = settings.getString(getString(R.string.prefAttempts_key), "5")!!.toInt()
        viewModel.modeGame = settings.getString(getString(R.string.prefGameMode_key), getString(R.string.prefGameMode_defaultValue))!!

        if(viewModel.modeGame == "Time") {
            lblAttemps_Points.text = getString(R.string.game_points)
        } else {
            lblAttemps_Points.text = getString(R.string.game_attempts)
        }
    }
    private fun inGame() {
        viewModel.refreshWord()

        viewModel.run {

            isFinish.observe(viewLifecycleOwner) {
                if(it) {
                    viewModel.insertGame()
                    navController.navigate(R.id.action_result_to_dashboard)
                }
            }

            indexWord.observe(viewLifecycleOwner) {
                wordRndGame.text = words[it]
            }
            indexColor.observe(viewLifecycleOwner) {
                wordRndGame.setTextColor(resources.getColor(colors[it]))
            }

            barProgress.observe(viewLifecycleOwner) {
                progressBarGame.progress = it
            }

            numCorrect.observe(viewLifecycleOwner) {
                pointsCorrect.text = it.toString()
            }
            numWords.observe(viewLifecycleOwner) {
                pointsWords.text = it.toString()
            }

            if(modeGame == "Time") {
                points.observe(viewLifecycleOwner) {
                    numAttemps_Points.text = it.toString()
                }
            } else {
                attempts.observe(viewLifecycleOwner) {
                    numAttemps_Points.text = it.toString()
                }
            }
        }
    }
    private fun observeAnswer() {
        btnCorrect.setOnClickListener {
            viewModel.checkRight()
        }

        btnWrong.setOnClickListener {
            viewModel.checkWrong()
        }
    }
}

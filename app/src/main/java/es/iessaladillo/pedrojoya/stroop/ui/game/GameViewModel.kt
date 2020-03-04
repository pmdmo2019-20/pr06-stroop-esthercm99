package es.iessaladillo.pedrojoya.stroop.ui.game

import android.app.Application
import android.content.SharedPreferences
import android.os.Handler
import android.widget.Toast
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.data.GameDao
import es.iessaladillo.pedrojoya.stroop.data.PlayerDao
import es.iessaladillo.pedrojoya.stroop.data.entity.Game
import es.iessaladillo.pedrojoya.stroop.data.entity.GamePlayer
import es.iessaladillo.pedrojoya.stroop.data.entity.Player
import kotlin.concurrent.thread
import kotlin.random.Random

class GameViewModel(private val playerDao: PlayerDao, private val gameDao: GameDao, private val application: Application) : ViewModel() {

    @Volatile
    private var isGameFinished: Boolean = false
    @Volatile
    private var currentWordMillis: Int = 0
    @Volatile
    private var millisUntilFinished: Int = 0
    private val handler: Handler = Handler()

    var isFinish: MutableLiveData<Boolean> = MutableLiveData(false)
    lateinit var modeGame: String

    var time: Int = -1
    val barProgress: MutableLiveData<Int> = MutableLiveData(millisUntilFinished)

    var attempts: MutableLiveData<Int> = MutableLiveData(5)
    var points: MutableLiveData<Int> = MutableLiveData(0)
    var numWords: MutableLiveData<Int> = MutableLiveData(0)
    var numCorrect: MutableLiveData<Int> = MutableLiveData(0)

    var player: Player = Player(-2, "No valid", -1L)

    var indexWord: MutableLiveData<Int> = MutableLiveData(0)
    var indexColor: MutableLiveData<Int> = MutableLiveData(0)

    val words = listOf("Green", "Red", "Yellow", "Blue")
    val colors = listOf(R.color.gameGreen, R.color.gameRed, R.color.gameYellow, R.color.gameBlue)

    private val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(application)
    }

    private fun onGameTimeTick(millisUntilFinished: Int) {
        barProgress.value = millisUntilFinished
    }

    private fun onGameTimeFinish() {
        isGameFinished = true
        isFinish.value = true
    }

    fun insertGame() {
        val correct = numCorrect.value!!
        val wordsShow = numWords.value!!.toInt()
        val game = Game(0, player.idUser.toInt(), modeGame, time, wordsShow, correct)
        thread {
            gameDao.insertGame(game)
        }
    }
    private fun nextWord() {
        refreshWord()
        numWords.value = numWords.value?.plus(1)
    }

    fun refreshWord() {
        indexWord.value = Random.nextInt(0, 4)
        indexColor.value = Random.nextInt(0, 4)
    }

    fun checkRight() {
        currentWordMillis = 0

        if (indexColor.value == indexWord.value) {
            points.value = points.value?.plus(10)
            numCorrect.value = numCorrect.value?.plus(1)
        } else {
            attempts.value = attempts.value?.minus(1)
        }

        if (attempts.value == 0 && modeGame == "Attempts") {
            onGameTimeFinish()
        } else {
            nextWord()
        }

    }

    fun checkWrong() {
        currentWordMillis = 0

        if (indexColor.value != indexWord.value) {
            points.value = points.value?.plus(10)
            numCorrect.value = numCorrect.value?.plus(1)
        } else {
            attempts.value = attempts.value?.minus(1)
        }

        if (attempts.value == 0 && modeGame == "Attempts") {
            onGameTimeFinish()
        } else {
            nextWord()
        }
    }

    fun startGameThread(gameTime: Int, wordTime: Int) {
        millisUntilFinished = gameTime
        currentWordMillis = 0
        isGameFinished = false
        val checkTimeMillis: Int = wordTime / 2
        thread {
            try {
                while (!isGameFinished) {
                    Thread.sleep(checkTimeMillis.toLong())
                    // Check and publish on main thread.
                    handler.post {
                        if (!isGameFinished) {
                            if (currentWordMillis >= wordTime) {
                                currentWordMillis = 0
                                nextWord()
                            }
                            currentWordMillis += checkTimeMillis
                            millisUntilFinished -= checkTimeMillis
                            onGameTimeTick(millisUntilFinished)
                            if (millisUntilFinished <= 0) {
                                onGameTimeFinish()
                            }
                        }
                    }
                }
            } catch (ignored: Exception) {
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        isGameFinished = true
    }
}
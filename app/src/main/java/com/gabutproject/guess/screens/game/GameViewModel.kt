package com.gabutproject.guess.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    companion object {
        // these are values for timer countdown
        // zero millisecond; actually use for reset value when timer is complete
        private const val DONE = 0L
        private const val ONE_SECOND = 1000L // 1 second in millisecond
        private const val TOTAL_TIME = 5000L // 1 minute in millisecond
    }

    // timer Countdown
    private lateinit var timer: CountDownTimer

    // current time to show
    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long> get() = _currentTime

    // current time<String> to show
    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

    // current word to show
    private val _word = MutableLiveData<String>()
    val word: LiveData<String> get() = _word

    // current score to show
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> get() = _score

    // tell the Fragment if the game has finished
    private val _onGameFinished = MutableLiveData<Boolean>()
    val onGameFinished: LiveData<Boolean> get() = _onGameFinished

    // the list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    init {
        Log.i("GameFragment", "game view model created")

        _word.value = ""
        _score.value = 0
        _onGameFinished.value = false

        resetList() // shuffle the words
        nextWord() // set the word to guess

        startTimer() // start the timer
    }

    /** Methods for logic **/
    private fun resetList() {
        wordList = mutableListOf(
            "ayam",
            "ikan",
            "kambing",
            "anjing",
            "kucing"
        )

        wordList.shuffle()
    }

    private fun nextWord() {
        if (wordList.isEmpty()) {
            resetList()
        }

        // pop the list till it's empty
        _word.value = wordList.removeAt(0)
    }

    private fun startTimer() {
        // set the timer to end after reached 1 minute
        // set to ticked in interval of 1 second
        timer = object : CountDownTimer(TOTAL_TIME, ONE_SECOND) {
            override fun onFinish() {
                _onGameFinished.value = true
                _currentTime.value = DONE // reset value to zero,
            }

            override fun onTick(millisUntilFinished: Long) {
                Log.i("GameViewModel", millisUntilFinished.toString())
                Log.i("GameViewModel", (millisUntilFinished / ONE_SECOND).toString())
                _currentTime.value = (millisUntilFinished / ONE_SECOND)
            }
        }

        // start the timer
        timer.start()
    }

    /** Methods for buttons presses **/
    fun onNext() {
        _score.value = _score.value?.plus(1)
        nextWord()
    }

    fun onSkip() {
        _score.value = _score.value?.minus(1)
        nextWord()
    }

    /** Methods to update the current state **/
    fun onGameFinishedComplete() {
        _onGameFinished.value = false
    }
}
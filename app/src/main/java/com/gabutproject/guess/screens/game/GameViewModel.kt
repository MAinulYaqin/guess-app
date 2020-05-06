package com.gabutproject.guess.screens.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    // current word to show
    private val _word = MutableLiveData<String>()
    val word: LiveData<String> get() = _word

    // current score to show
    private val _score = MutableLiveData<Int>()
    val score : LiveData<Int> get() = _score

    // tell the Fragment if the game has finished
    private val _onGameFinished = MutableLiveData<Boolean>()
    val onGameFinished : LiveData<Boolean> get() = _onGameFinished

    // the list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    init {

        Log.i("GameFragment", "game view model created")

        _word.value = ""
        _score.value = 0
        _onGameFinished.value = false

        resetList() // shuffle the words
        nextWord() // set the word to guess
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
        if (wordList.isEmpty() || _score.value == 3) {
            // wordList is empty / score = 3, the game finished
            _onGameFinished.value = true
        } else {
            // pop the list till empty
            _word.value = wordList.removeAt(0)
        }
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

    fun onGameFinishedComplete() {
        _onGameFinished.value = false
    }
}
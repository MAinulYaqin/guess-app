package com.gabutproject.guess.screens.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gabutproject.guess.R

class ScoreViewModel(totalScore: Int) : ViewModel() {

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> get() = _score

    // user's status. etc: win or lose
    // returns string resources of the following status
    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int> get() = _status

    // if true, will occurs the event to navigate to another screen
    private val _backHome = MutableLiveData<Boolean>()
    val backHome: LiveData<Boolean> get() = _backHome

    init {
        _score.value = totalScore
        _status.value = if (totalScore >= 3) R.string.you_win else R.string.you_lose
    }

    fun onBackHome() {
        _backHome.value = true
    }

    fun onBackHomeComplete() {
        _backHome.value = false
    }
}

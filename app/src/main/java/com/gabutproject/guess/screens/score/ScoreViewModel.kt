package com.gabutproject.guess.screens.score

import androidx.lifecycle.ViewModel
import com.gabutproject.guess.R

class ScoreViewModel(totalScore: Int) : ViewModel() {
    var score = totalScore

    // user's status. etc: win or lose
    // returns string resources of the following status
    var status: Int = if (score >= 3) R.string.you_win else R.string.you_lose
}

package com.gabutproject.guess.screens.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gabutproject.guess.R
import com.gabutproject.guess.databinding.GameFragmentBinding

// TODO: make viewModel for this activity

class GameFragment : Fragment() {

    // current word to show
    private var word: String? = ""

    // current score to show
    private var score = 0

    // the list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)

        resetList() // shuffle the words
        nextWord() // set the word to guess

        binding.nextButton.setOnClickListener { onNext() }
        binding.skipButton.setOnClickListener { onSkip() }

        // once initialization to show the word & score
        updateWord()
        updateScore()

        return binding.root
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
            // wordList is empty, the game finished
            gameFinished()
        } else {
            // pop the list till empty
            word = wordList.removeAt(0)
        }
        updateWord()
        updateScore()
    }

    private fun gameFinished() {
        val action = R.id.action_gameFragment_to_scoreFragment
        // TODO: pass safeArgs to score
        findNavController().navigate(action)
    }

    /** Methods for buttons presses **/
    private fun onNext() {
        score++
        nextWord()
    }

    private fun onSkip() {
        score--
        nextWord()
    }

    /** Methods updating the UI **/
    private fun updateWord() {
        // bind text to show the current word
        binding.questionText.text = word
    }

    private fun updateScore() {
        // bind text to show the current score
        binding.currentScoreText.text = getString(R.string.current_score_text, score)
    }
}

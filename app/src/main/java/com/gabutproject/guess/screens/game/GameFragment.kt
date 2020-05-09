package com.gabutproject.guess.screens.game

import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.gabutproject.guess.R
import com.gabutproject.guess.databinding.GameFragmentBinding

class GameFragment : Fragment() {

    private val viewModel: GameViewModel by viewModels()
    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)

        // data initialization
        viewModel

        binding.nextButton.setOnClickListener {
            viewModel.onNext()
        }
        binding.skipButton.setOnClickListener {
            viewModel.onSkip()
        }

        updateLiveData()

        return binding.root
    }

    private fun gameFinished() {
        val score: Int = viewModel.score.value!!
        val action = GameFragmentDirections.actionGameFragmentToScoreFragment(score)
        findNavController().navigate(action)
    }

    /** Methods updating the data **/
    private fun updateLiveData() {
        viewModel.score.observe(viewLifecycleOwner, Observer { currentScore ->
            updateScore(currentScore)
        })

        viewModel.word.observe(viewLifecycleOwner, Observer { currentWord ->
            updateWord(currentWord)
        })

        viewModel.currentTime.observe(viewLifecycleOwner, Observer { currentTime ->
            updateTimer(currentTime)
        })

        viewModel.onGameFinished.observe(viewLifecycleOwner, Observer { onGameFinished ->
            if (onGameFinished) {
                // make directions to another fragment
                gameFinished()
                // and called gameFinishedComplete() to change the current state's
                // value to false to prevent re-called observer
                viewModel.onGameFinishedComplete()
            }
        })
    }

    /** Methods to update UI; current state **/
    private fun updateWord(word: String) {
        // bind text to show the current word
        binding.questionText.text = word
    }

    private fun updateScore(score: Int) {
        // bind text to show the current score
        binding.currentScoreText.text = getString(R.string.current_score_text, score)
    }

    private fun updateTimer(timer: Long) {
        // use dateUtils here, to reduce code
        binding.timerText.text = DateUtils.formatElapsedTime(timer)
    }
}

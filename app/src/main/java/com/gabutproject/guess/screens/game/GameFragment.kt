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
        viewModel.score.observe(viewLifecycleOwner, Observer {
            updateScore(it)
        })

        viewModel.word.observe(viewLifecycleOwner, Observer {
            updateWord(it)
        })

        viewModel.currentTime.observe(viewLifecycleOwner, Observer {
            updateTimer(it)
        })

        viewModel.onGameFinished.observe(viewLifecycleOwner, Observer { gameFinished ->
            if (gameFinished) {
                gameFinished() // make directions to another fragment
                // set data to false to prevent re-called observer
                viewModel.onGameFinishedComplete()
            }
        })
    }

    /** Methods for button presses **/
    private fun updateWord(word: String) {
        // bind text to show the current word
        binding.questionText.text = word
    }

    private fun updateScore(score: Int) {
        // bind text to show the current score
        binding.currentScoreText.text = getString(R.string.current_score_text, score)
    }

    private fun updateTimer(timer: Long) {
        binding.timerText.text = DateUtils.formatElapsedTime(timer)
    }
}

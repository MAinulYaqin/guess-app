package com.gabutproject.guess.screens.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gabutproject.guess.R
import com.gabutproject.guess.databinding.ScoreFragmentBinding

class ScoreFragment : Fragment() {

    private lateinit var binding: ScoreFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.score_fragment, container, false)

        binding.backButton.setOnClickListener {
            findNavController().navigate(ScoreFragmentDirections.actionScoreFragmentToTitleFragment())
        }

        showScore()

        return binding.root
    }

    private fun showScore() {
        val args = arguments?.let { ScoreFragmentArgs.fromBundle(it) }

        if (args != null && args.totalScore >= 3) {
            // score is equal or higher than 3, you win
            binding.statusText.text = getString(R.string.you_win)
        } else {
            // score is less than 3, you lose
            binding.statusText.text = getString(R.string.you_lose)
        }

        binding.summarizeText.text = getString(R.string.final_score_text, args?.totalScore)
    }
}
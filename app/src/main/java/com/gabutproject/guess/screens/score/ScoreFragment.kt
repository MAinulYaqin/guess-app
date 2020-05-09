package com.gabutproject.guess.screens.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gabutproject.guess.R
import com.gabutproject.guess.databinding.ScoreFragmentBinding

class ScoreFragment : Fragment() {

    private lateinit var viewModel: ScoreViewModel

    private lateinit var viewModelFactory: ScoreViewModelFactory

    private lateinit var binding: ScoreFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.score_fragment, container, false)

        // send arguments to factory to pass to the viewModel
        val totalScore = ScoreFragmentArgs.fromBundle(requireArguments()).totalScore
        viewModelFactory = ScoreViewModelFactory(totalScore)

        // get the data from modalProvider then init the viewModel
        viewModel = ViewModelProvider(this, viewModelFactory).get(ScoreViewModel::class.java)

        // viewModel.status contains status of the user, whether they win or lose
        // returns string resource with the value of the following status
        binding.statusText.text = getString(viewModel.status)

        binding.summarizeText.text = getString(R.string.final_score_text, viewModel.score)

        binding.backButton.setOnClickListener {
            val action = ScoreFragmentDirections.actionScoreFragmentToTitleFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }
}

package com.gabutproject.guess.screens.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        gameFinished()

        return binding.root
    }

    private fun gameFinished() {
        viewModel.backHome.observe(viewLifecycleOwner, Observer { onGameFinished ->
            if (onGameFinished) {
                // make directions to another fragment
                val action = ScoreFragmentDirections.actionScoreFragmentToTitleFragment()
                findNavController().navigate(action)

                // set the value to false again,
                viewModel.onBackHomeComplete()
            }
        })
    }

}

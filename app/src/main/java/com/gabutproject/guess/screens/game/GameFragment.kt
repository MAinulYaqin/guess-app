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

        // Set viewModel for dataBinding - this allows the bound layout access
        // to all data in the viewModel, so no need to set data here anymore.
        binding.gameViewModel = viewModel

        // to make the LiveData work
        // set lifecycleOwner to currentActivity (the UI controller)
        binding.lifecycleOwner = viewLifecycleOwner

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
}

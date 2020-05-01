package com.gabutproject.guess.screens.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.gabutproject.guess.R
import com.gabutproject.guess.databinding.ScoreFragmentBinding

class ScoreFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: ScoreFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.score_fragment, container, false)

        binding.backButton.setOnClickListener {
            Navigation.createNavigateOnClickListener(R.id.action_scoreFragment_to_titleFragment)
        }

        return binding.root
    }
}
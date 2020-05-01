package com.gabutproject.guess.screens.title

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.gabutproject.guess.databinding.TitleFragmentBinding

import com.gabutproject.guess.R

class TitleFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: TitleFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.title_fragment, container, false)

        binding.playButton.setOnClickListener {
            Navigation.createNavigateOnClickListener(R.id.action_titleFragment_to_gameFragment)
        }

        return binding.root
    }
}
package com.example.mytinynasa.mars_rover.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mytinynasa.R
import com.example.mytinynasa.mars_rover.viewmodel.MarsRoverViewModel

class MarsRover : Fragment() {

    companion object {
        fun newInstance() = MarsRover()
    }

    private lateinit var viewModel: MarsRoverViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.mars_rover_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MarsRoverViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
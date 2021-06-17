package com.example.mytinynasa.mars_rover.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mytinynasa.R

/**
 * A simple [Fragment] subclass.
 * Use the [MarsRoverCameraSelection.newInstance] factory method to
 * create an instance of this fragment.
 */
class MarsRoverCameraSelection (roverModel: String): Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mars_rover_camera_curiosity, container, false)
    }
}
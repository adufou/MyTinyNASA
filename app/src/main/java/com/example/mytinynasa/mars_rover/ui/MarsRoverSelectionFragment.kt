package com.example.mytinynasa.mars_rover.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentTransaction
import com.example.mytinynasa.R

class  MarsRoverSelectionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_mars_rover_selection, container, false)

        val buttonCuriosity = root.findViewById(R.id.rover_curiosity_cam_MAST) as Button
        val buttonOpportunity = root.findViewById(R.id.rover_curiosity_cam_RHAZ) as Button
        val buttonSpirit = root.findViewById(R.id.rover_curiosity_cam_FHAZ) as Button

        buttonCuriosity.setOnClickListener() {
            val cameraSelection = MarsRoverCameraSelection("Curiosity")
            val transaction : FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment_activity_main, cameraSelection)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        buttonOpportunity.setOnClickListener() {
            val cameraSelection = MarsRoverCameraSelection("Opportunity")
            val transaction : FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment_activity_main, cameraSelection)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        buttonSpirit.setOnClickListener() {
            val cameraSelection = MarsRoverCameraSelection("Spirit")
            val transaction : FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment_activity_main, cameraSelection)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return root
    }
}
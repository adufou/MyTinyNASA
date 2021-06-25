package com.example.mytinynasa.mars_rover.ui

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentTransaction
import com.example.mytinynasa.R

class MarsRoverSelectionFragment : Fragment() {
    var settings: SharedPreferences? = null
    var settings_editor: SharedPreferences.Editor? = null


    fun switchToRoverFragment(roverName: String) {
        settings_editor?.putString("rover", roverName)
        settings_editor?.putString("camera", null)
        settings_editor?.commit()
        val roverFragment = MarsRoverFragment()
        val transaction: FragmentTransaction = parentFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.mars_rover_rover_selection, roverFragment)
        transaction.commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        settings = context?.getSharedPreferences("MARS", 0)!!
        settings_editor = settings!!.edit()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mars_rover_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonCuriosity = requireView().findViewById(R.id.rover_curiosity_cam_MAST) as Button
        val buttonOpportunity = requireView().findViewById(R.id.rover_curiosity_cam_RHAZ) as Button
        val buttonSpirit = requireView().findViewById(R.id.rover_curiosity_cam_FHAZ) as Button

        when (settings?.getString("rover", "")) {
            "Curiosity" -> buttonCuriosity.setBackgroundColor(Color.parseColor("#F9627D"))
            "Opportunity" -> buttonOpportunity.setBackgroundColor(Color.parseColor("#F9627D"))
            "Spirit" -> buttonSpirit.setBackgroundColor(Color.parseColor("#F9627D"))
        }

        buttonCuriosity.setOnClickListener() {
            switchToRoverFragment("Curiosity")
        }

        buttonOpportunity.setOnClickListener() {
            switchToRoverFragment("Opportunity")
        }

        buttonSpirit.setOnClickListener() {
            switchToRoverFragment("Spirit")
        }
    }
}
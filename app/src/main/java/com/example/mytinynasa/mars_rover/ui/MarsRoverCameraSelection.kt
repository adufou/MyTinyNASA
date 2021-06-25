package com.example.mytinynasa.mars_rover.ui

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.mytinynasa.R

/**
 * A simple [Fragment] subclass.
 * Use the [MarsRoverCameraSelection.newInstance] factory method to
 * create an instance of this fragment.
 */
class MarsRoverCameraSelection : Fragment() {
    var settings: SharedPreferences? = null
    var settings_editor: SharedPreferences.Editor? = null
    lateinit var linearLayout: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        settings = context?.getSharedPreferences("MARS", Context.MODE_PRIVATE)!!
        settings_editor = settings!!.edit()
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_mars_rover_camera_selection, container, false)
        linearLayout = rootView.findViewById(R.id.mars_rover_camera_selection_list)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val actualRover: String? = settings?.getString("rover", "Curiosity")
        val actualCamera: String? = settings?.getString("camera", "")
        val checkCamera = actualCamera != ""
        addCameraButton("None", "", true, actualCamera)
        if (actualRover != null) {
            getCameraList(actualRover)?.forEach { camera ->
                run {
                    val data = camera.split(",")
                    addCameraButton(data[1], data[0], checkCamera, actualCamera)
                }
            }
        }
    }

    fun addCameraButton(text: String, value: String, checkCamera: Boolean, actualCamera: String?)
    {
        val button = Button(this.context)
        button.text = text
        button.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        if (checkCamera && actualCamera.equals(value))
            button.setBackgroundColor(Color.parseColor("#F9627D"))

        button.setOnClickListener {
            settings_editor!!.putString("camera", value)
            this.settings_editor!!.commit()
            switchToRoverFragment()
        }
        this.linearLayout.addView(button)
    }


    fun getCameraList(actualRover: String?): Array<out String>? {
        try {
            if (actualRover != null) {
                val resourceId = resources.getIdentifier(actualRover, "array", this.context?.packageName)
                Log.d("ResourceId", "Id $resourceId")
                return resources.getStringArray(resourceId)
            }
        } catch (e: Resources.NotFoundException) {
            val error = e.message
        }
        return null
    }

    fun switchToRoverFragment() {
        val roverFragment = MarsRoverFragment()
        val transaction : FragmentTransaction = parentFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.mars_rover_camera_selection_fragment, roverFragment)
        transaction.commit()
    }

}
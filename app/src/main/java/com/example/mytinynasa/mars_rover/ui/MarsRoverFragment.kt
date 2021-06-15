package com.example.mytinynasa.mars_rover.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mytinynasa.R
import com.example.mytinynasa.mars_rover.MarsRoverActivity

class MarsRoverFragment : Fragment() {

    companion object {
        fun newInstance() = MarsRoverFragment()
    }

    private lateinit var activity: MarsRoverActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.mars_rover_item_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //activity = ViewModelProvider(this).get(MarsRoverActivity::class.java)
        // TODO: Use the ViewModel
    }

}
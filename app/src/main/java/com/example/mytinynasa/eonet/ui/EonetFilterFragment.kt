package com.example.mytinynasa.eonet.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mytinynasa.R

/**
 * A simple [Fragment] subclass.
 * Use the [EonetFilterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EonetFilterFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_eonet_filter, container, false)
    }
}
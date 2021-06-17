package com.example.mytinynasa.ui.apod

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mytinynasa.R

class ApodFragment : Fragment() {

    companion object {
        fun newInstance() = ApodFragment()
    }

    private lateinit var viewModel: ApodViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.apod_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ApodViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun getAPODPhotos(apiKey: String) {

    }

}
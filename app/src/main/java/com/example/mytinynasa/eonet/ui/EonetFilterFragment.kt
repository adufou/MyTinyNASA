package com.example.mytinynasa.eonet.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.FragmentTransaction
import com.example.mytinynasa.R
import com.google.android.material.button.MaterialButton

/**
 * A simple [Fragment] subclass.
 * Use the [EonetFilterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EonetFilterFragment : Fragment() {
    var settings : SharedPreferences? = null// = context?.getSharedPreferences("EONET", 0)!!
    var settings_editor : SharedPreferences.Editor? = null// = settings.edit()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settings = context?.getSharedPreferences("EONET", 0)!!
        settings_editor = settings!!.edit()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_eonet_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apply : MaterialButton = requireView().findViewById(R.id.eonet_filter_apply)
        apply.setOnClickListener {
            val limit_in : EditText = requireView().findViewById(R.id.eonet_filter_limit)
            var limit = 25;

            limit_in.text?.toString()?.let { text ->
                if (text != "")
                    limit = Integer.parseInt(text)
            }

            settings_editor!!.putInt("eonet_limit", limit)
            settings_editor!!.commit()

            val eonetFragment = EonetFragment()
            val transaction : FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.eonet_filter_fragment, eonetFragment)
            transaction.commit()
        }
    }
}
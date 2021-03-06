package com.example.mytinynasa.eonet.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import androidx.core.content.ContextCompat.getSystemService
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

    // Override function onCreateView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settings = context?.getSharedPreferences("EONET", 0)!!
        settings_editor = settings!!.edit()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_eonet_filter, container, false)
    }

    // Override function onViewCreated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Call same function on Super
        super.onViewCreated(view, savedInstanceState)

        // Identify apply filter button
        // Set Listener on click
        val apply : MaterialButton = requireView().findViewById(R.id.eonet_filter_apply)
        apply.setOnClickListener {
            // CLOSE KEYBOARD
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val filter_view : FrameLayout = requireView().findViewById(R.id.eonet_filter_fragment)
            imm.hideSoftInputFromWindow(filter_view.windowToken, 0)

            // GET LIMIT
            val limit_in : EditText = requireView().findViewById(R.id.eonet_filter_limit)
            var limit = 25;

            limit_in.text?.toString()?.let { text ->
                if (text != "")
                    limit = Integer.parseInt(text)
            }

            // GET DAYS
            val days_in : EditText = requireView().findViewById(R.id.eonet_filter_days)
            var days = 7;

            days_in.text?.toString()?.let { text ->
                if (text != "")
                    days = Integer.parseInt(text)
            }

            // COMMIT SETTINGS
            settings_editor!!.putInt("eonet_limit", limit)
            settings_editor!!.putInt("eonet_days", days)
            settings_editor!!.commit()

            // TRANSACTION
            val eonetFragment = EonetFragment()
            val transaction : FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.eonet_filter_fragment, eonetFragment)
            transaction.commit()
        }
    }
}
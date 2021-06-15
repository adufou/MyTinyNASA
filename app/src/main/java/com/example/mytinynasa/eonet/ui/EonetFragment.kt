package com.example.mytinynasa.eonet.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytinynasa.R
import com.example.mytinynasa.eonet.data.Event

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EonetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EonetFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.eonet_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data : List<Event> = mutableListOf(
            Event("EONET_5360", "Tropical Storm Blanca", null, "https://eonet.sci.gsfc.nasa.gov/api/v3/events/EONET_5360", "2021-06-09T00:00:00Z", null, null, null),
            Event("EONET_5356", "Tropical Storm Choi-wan", null, "https://eonet.sci.gsfc.nasa.gov/api/v3/events/EONET_5356", "2021-06-10T00:00:00Z", null, null, null),
            Event("EONET_5360", "Tropical Storm Blanca", null, "https://eonet.sci.gsfc.nasa.gov/api/v3/events/EONET_5360", "2021-06-09T00:00:00Z", null, null, null),
            Event("EONET_5356", "Tropical Storm Choi-wan", null, "https://eonet.sci.gsfc.nasa.gov/api/v3/events/EONET_5356", "2021-06-10T00:00:00Z", null, null, null),
            Event("EONET_5360", "Tropical Storm Blanca", null, "https://eonet.sci.gsfc.nasa.gov/api/v3/events/EONET_5360", "2021-06-09T00:00:00Z", null, null, null),
            Event("EONET_5356", "Tropical Storm Choi-wan", null, "https://eonet.sci.gsfc.nasa.gov/api/v3/events/EONET_5356", "2021-06-10T00:00:00Z", null, null, null),
            Event("EONET_5360", "Tropical Storm Blanca", null, "https://eonet.sci.gsfc.nasa.gov/api/v3/events/EONET_5360", "2021-06-09T00:00:00Z", null, null, null),
            Event("EONET_5356", "Tropical Storm Choi-wan", null, "https://eonet.sci.gsfc.nasa.gov/api/v3/events/EONET_5356", "2021-06-10T00:00:00Z", null, null, null),
            Event("EONET_5360", "Tropical Storm Blanca", null, "https://eonet.sci.gsfc.nasa.gov/api/v3/events/EONET_5360", "2021-06-09T00:00:00Z", null, null, null),
            Event("EONET_5356", "Tropical Storm Choi-wan", null, "https://eonet.sci.gsfc.nasa.gov/api/v3/events/EONET_5356", "2021-06-10T00:00:00Z", null, null, null),
        )

        val eonetRecyclerView : RecyclerView = requireView().findViewById(R.id.eonet_recycler)
        eonetRecyclerView.layoutManager = LinearLayoutManager(context)
        eonetRecyclerView.setHasFixedSize(true)
        eonetRecyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

        if (data != null)
            eonetRecyclerView.adapter = EonetAdapter(data)
        else {
            eonetRecyclerView.visibility = View.GONE
            val eonetEmptyTextView : TextView = requireView().findViewById(R.id.fragment_eonet_emptylist)
            eonetEmptyTextView.visibility = View.VISIBLE
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EonetFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EonetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
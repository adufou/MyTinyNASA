package com.example.mytinynasa.eonet.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytinynasa.R
import com.example.mytinynasa.eonet.data.EonetResult
import com.example.mytinynasa.eonet.data.Event
import com.example.mytinynasa.network.ApiClient
import com.example.mytinynasa.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 * Use the [EonetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EonetFragment : Fragment() {
    var settings : SharedPreferences? = null// = context?.getSharedPreferences("EONET", 0)!!
    var settings_editor : SharedPreferences.Editor? = null// = settings.edit()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settings = context?.getSharedPreferences("EONET", 0)!!
        settings_editor = settings!!.edit()

        //settings_editor!!.putInt("Count", 0)
        //settings_editor!!.apply()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.eonet_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var data = arrayListOf<Event>();

        val eonetRecyclerView : RecyclerView = requireView().findViewById(R.id.eonet_recycler)
        eonetRecyclerView.layoutManager = LinearLayoutManager(context)
        eonetRecyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

        // RETROFIT
        val retrofit = ApiClient.getApiClient("https://eonet.sci.gsfc.nasa.gov/api/v3/")
        val service = retrofit.create(ApiInterface::class.java)

        val callback : Callback<EonetResult> = object : Callback<EonetResult> {
            override fun onResponse(call: Call<EonetResult>, response: Response<EonetResult>) {
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        Log.d("MyTinyNasa", "EONET API Success : " + body.toString())

                        data = body.events as ArrayList<Event>
                        eonetRecyclerView.adapter = EonetAdapter(data)
                    }
                }
                else {
                    Log.d("MyTinyNasa", "EONET API Error : " + response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<EonetResult>, t: Throwable) {
                Log.d("MyTinyNasa", "EONET API Error : " + t.message)
            }

        }

        var limit : Int = 25
        var days : Int = 7

        settings?.getInt("eonet_limit", 25)?.let { it -> limit = it }
        settings?.getInt("eonet_days", 7)?.let { it -> days = it }

        service.getEonetEvents(null, null, null, limit, days, null, null, null, null, null, null).enqueue(callback)

        val filer_button : Button = requireView().findViewById(R.id.eonet_filter_button)
        filer_button.setOnClickListener {
            val filter_fragment = EonetFilterFragment()
            val transaction : FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.eonet_fragment, filter_fragment)
            transaction.commit()
        }

        //if (data != null)

       /* else {
            eonetRecyclerView.visibility = View.GONE
            val eonetEmptyTextView : TextView = requireView().findViewById(R.id.fragment_eonet_emptylist)
            eonetEmptyTextView.visibility = View.VISIBLE
        }*/

    }
}
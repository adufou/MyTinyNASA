package com.example.mytinynasa.apod.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytinynasa.R
import com.example.mytinynasa.apod.data.ApodModel
import com.example.mytinynasa.apod.ui.ApodAdapter
import com.example.mytinynasa.network.ApiClient
import com.example.mytinynasa.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [ApodFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ApodFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_apod, container, false)
    }

    private fun getApodPhotos(recyclerView: RecyclerView, apiKey: String) {

        val retrofit = ApiClient.getApiClient("https://api.nasa.gov/")

        val service = retrofit.create(ApiInterface::class.java)

        var results : List<ApodModel>

        val callback : Callback<List<ApodModel>> = object : Callback<List<ApodModel>> {
            override fun onFailure(call: Call<List<ApodModel>>, t: Throwable) {
                Log.d("MyTinyNasa", "API Error " + t.message)
            }

            override fun onResponse(
                call: Call<List<ApodModel>>,
                response: Response<List<ApodModel>>
            ) {

                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        context?.let { context ->
                            Toast.makeText(context, "SUCCESS, found " + data.size, Toast.LENGTH_SHORT).show()
                        }
                    }
                    results = response.body()!!
                    recyclerView.adapter = ApodAdapter(results)
                } else {
                    Log.d("MyTinyNasa", "API Error : " + response.errorBody().toString())
                }
            }
        }

        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val end_date = simpleDateFormat.format(Date().time)
        val start_date_long = Date().time - (6 * 24 * 3600 * 1000)
        val start_date = simpleDateFormat.format(start_date_long)

        service.getApodPhotos(start_date, end_date, apiKey).enqueue(callback)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apodRecyclerView : RecyclerView = requireView().findViewById(R.id.apod_recycler)
        apodRecyclerView.layoutManager = LinearLayoutManager(context)
        apodRecyclerView.setHasFixedSize(true)
        apodRecyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

        getApodPhotos(apodRecyclerView, ApiClient.apiKey)
    }

}
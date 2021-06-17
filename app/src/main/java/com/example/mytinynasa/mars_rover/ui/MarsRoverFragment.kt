package com.example.mytinynasa.mars_rover.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytinynasa.R
import com.example.mytinynasa.mars_rover.data.MarsRoverResult
import com.example.mytinynasa.network.ApiClient
import com.example.mytinynasa.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [MarsRoverFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MarsRoverFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mars_rover, container, false)
    }

    private fun getMarsPhotos(recyclerView: RecyclerView, roverType: String?, cameraType: String?, apiKey: String) {

        val retrofit = ApiClient.getApiClient("https://api.nasa.gov/")

        val service = retrofit.create(ApiInterface::class.java)

        var results : MarsRoverResult

        val callback : Callback<MarsRoverResult> = object : Callback<MarsRoverResult> {
            override fun onFailure(call: Call<MarsRoverResult>, t: Throwable) {
                Log.d("MyTinyNasa", "API Error " + t.message)
            }

            override fun onResponse(
                call: Call<MarsRoverResult>,
                response: Response<MarsRoverResult>
            ) {

                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        Toast.makeText(context, "SUCCESS, found " + data.photos.size, Toast.LENGTH_SHORT).show()
                    }
                    results = response.body()!!
                    recyclerView.adapter = MarsRoverAdapter(results.photos)
                } else {
                    Log.d("MyTinyNasa", "API Error : " + response.errorBody().toString())
                }
            }
        }

        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val previousDate = Date().time - (3600 * 24 * 1000)
        val newDate = simpleDateFormat.format(Date(previousDate))

        service.getMarsPhotos(roverType, cameraType, apiKey, newDate).enqueue(callback)

        /*
        var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        var previousDate = Date().time - (3600 * 24 * 1000)
        var newDate = simpleDateFormat.format(Date(previousDate))
        var result = service.getMarsPhotos(roverType, cameraType, apiKey, newDate)
        var exec = result.execute()
        while (exec.isSuccessful && exec.body()!!.photos.isEmpty(){
            simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE)
            previousDate -= (3600 * 24 * 1000)
            newDate = simpleDateFormat.format(Date(previousDate))
            result = service.getMarsPhotos(roverType, cameraType, apiKey, newDate)
            exec = result.execute()
        }
        result.enqueue(callback)
         */

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val marsRoverRecyclerView : RecyclerView = requireView().findViewById(R.id.mars_rover_recycler)
        marsRoverRecyclerView.layoutManager = LinearLayoutManager(context)
        marsRoverRecyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

        getMarsPhotos(marsRoverRecyclerView, "Curiosity", null, ApiClient.apiKey)
    }
}
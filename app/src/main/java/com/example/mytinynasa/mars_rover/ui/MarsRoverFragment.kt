package com.example.mytinynasa.mars_rover.ui

import androidx.lifecycle.ViewModelProvider
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
import com.example.mytinynasa.mars_rover.data.MarsRoverAdapter
import com.example.mytinynasa.mars_rover.data.MarsRoverResult
import com.example.mytinynasa.network.ApiClient
import com.example.mytinynasa.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class MarsRoverFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.mars_rover_item_list, container, false)
    }

    private fun getMarsPhotos(recyclerView: RecyclerView, onItemClickListener : View.OnClickListener, roverType: String?, cameraType: String?, apiKey: String) {

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
                    recyclerView.adapter = MarsRoverAdapter(results.photos, onItemClickListener)
                } else {
                    Log.d("MyTinyNasa", "API Error : " + response.errorBody().toString())
                }
            }
        }
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val previousDate = Date().time - (3600 * 24 * 1000)
        val newDate = simpleDateFormat.format(Date(previousDate))

        service.getMarsPhotos(roverType, cameraType, apiKey, newDate).enqueue(callback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val marsRoverRecyclerView : RecyclerView = requireView().findViewById(R.id.mars_rover_recycler)
        marsRoverRecyclerView.layoutManager = LinearLayoutManager(context)
        marsRoverRecyclerView.setHasFixedSize(true)
        marsRoverRecyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

        val onItemClickListener : View.OnClickListener = object : View.OnClickListener {
            override fun onClick(clickedItemView: View?) {
                val position : Int = clickedItemView!!.tag as Int
                Toast.makeText(context, "Clicked at index $position", Toast.LENGTH_SHORT).show()
            }
        }

        getMarsPhotos(marsRoverRecyclerView, onItemClickListener, "Curiosity", null, ApiClient.apiKey)
    }
}
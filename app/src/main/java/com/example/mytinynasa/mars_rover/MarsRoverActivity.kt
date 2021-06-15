package com.example.mytinynasa.mars_rover


import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mytinynasa.mars_rover.data.MarsRoverModel
import com.example.mytinynasa.network.ApiClient
import com.example.mytinynasa.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarsRoverActivity : Activity() {

    fun getMarsPhotos(roverType : String, cameraType : String, apiKey : String) : LiveData<List<MarsRoverModel>> {
        val data = MutableLiveData<List<MarsRoverModel>>()

        val retrofit = ApiClient.getApiClient("https://api.nasa.gov/")

        val service = retrofit.create(ApiInterface::class.java)

        val callback : Callback<List<MarsRoverModel>> = object : Callback<List<MarsRoverModel>> {
            override fun onFailure(call: Call<List<MarsRoverModel>>, t: Throwable) {
                Log.d("MyTinyNasa", "API Error " + t.message)
            }

            override fun onResponse(
                call: Call<List<MarsRoverModel>>,
                response: Response<List<MarsRoverModel>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        Toast.makeText(this@MarsRoverActivity, "SUCCESS, found " + data.size, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("MyTinyNasa", "API Error " + t.message)
                }
            }
        }

        service.getMarsPhotos(roverType, cameraType, apiKey).enqueue(callback)
    }
}
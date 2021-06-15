package com.example.mytinynasa.mars_rover


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytinynasa.R
import com.example.mytinynasa.mars_rover.data.MarsRoverAdapter
import com.example.mytinynasa.mars_rover.data.MarsRoverModel
import com.example.mytinynasa.network.ApiClient
import com.example.mytinynasa.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarsRoverActivity : AppCompatActivity() {

    fun getMarsPhotos(roverType: String?, cameraType: String?, apiKey: String) : LiveData<List<MarsRoverModel>> {

        val retrofit = ApiClient.getApiClient("https://api.nasa.gov/")

        val service = retrofit.create(ApiInterface::class.java)

        val results : List<MarsRoverModel>

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
                    Log.d("MyTinyNasa", "API Error : " + response.errorBody().toString())
                }
            }
        }

        service.getMarsPhotos(roverType, cameraType, apiKey).enqueue(callback)
        return results
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mars_rover_item_list)

        val data : List<MarsRoverModel>? = getMarsPhotos("Curiosity", null, ApiClient.apiKey).value

        val MarsRoverRecyclerView : RecyclerView = findViewById(R.id.mars_rover_activity_list)
        MarsRoverRecyclerView.layoutManager = LinearLayoutManager(this@MarsRoverActivity)
        MarsRoverRecyclerView.setHasFixedSize(true)
        MarsRoverRecyclerView.addItemDecoration(DividerItemDecoration(this@MarsRoverActivity, LinearLayoutManager.VERTICAL))
        val onItemClickListener : View.OnClickListener = object : View.OnClickListener {
            override fun onClick(clickedItemView: View?) {
                val position : Int = clickedItemView!!.tag as Int
                Toast.makeText(this@MarsRoverActivity, "Clicked at index " + position, Toast.LENGTH_SHORT).show()
            }
        }
        if (data != null) {
            MarsRoverRecyclerView.adapter = MarsRoverAdapter(data, onItemClickListener)
        }
    }
}
package com.example.mytinynasa.network

import com.example.mytinynasa.mars_rover.data.MarsRoverModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("https://api.nasa.gov/mars-photos/api/v1/rovers/{roverType}/photos")
    fun getMarsPhotos(@Path(value="roverType", encoded = true) roverType : String, @Query(value="camera") cameraType : String, @Query(value="api_key") apiKey : String) : Call<List<MarsRoverModel>>
}
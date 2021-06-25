package com.example.mytinynasa.network

import com.example.mytinynasa.apod.data.ApodModel
import com.example.mytinynasa.eonet.data.EonetResult
import com.example.mytinynasa.mars_rover.data.MarsRoverResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("mars-photos/api/v1/rovers/{roverType}/photos")
    fun getMarsPhotos(@Path(value="roverType", encoded = true) roverType : String?,
                      @Query(value="camera") cameraType : String?,
                      @Query(value="api_key") apiKey : String,
                      @Query(value="earth_date") date : String?,
                      @Query(value="sol") number: Number? = 10,
                      @Query(value="page") page : String? = "1") : Call<MarsRoverResult>

    @GET("events")
    fun getEonetEvents(
        @Query(value = "source") source : String?,
        @Query(value = "category") category : String?,
        @Query(value = "status") status : String?,
        @Query(value = "limit") limit : Int?,
        @Query(value = "days") days : Int?,
        @Query(value = "start") start : String?,
        @Query(value = "end") end : String?,
        @Query(value = "magID") magID : String?,
        @Query(value = "magMin") magMin : Float?,
        @Query(value = "magMax") magMax : Float?,
        @Query(value = "bbox") bbox : String?
    ) : Call<EonetResult>

    @GET("planetary/apod")
    fun getApodPhotos(
        @Query(value = "start_date") start_date : String,
        @Query(value = "end_date") end_date : String,
        @Query(value = "api_key") apiKey : String
    ) : Call<List<ApodModel>>
}
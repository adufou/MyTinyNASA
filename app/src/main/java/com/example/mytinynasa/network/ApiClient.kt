package com.example.mytinynasa.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {

        val apiKey = "MwyRNpzVidCbG6RmQ1zqiqVriYSdJThatCRIMQi6"

        private var retrofit:Retrofit?=null

        fun getApiClient(baseUrl: String) : Retrofit {
            var converter = GsonConverterFactory.create(GsonBuilder().create())
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converter)
                .build()

            return retrofit!!
        }
    }
}
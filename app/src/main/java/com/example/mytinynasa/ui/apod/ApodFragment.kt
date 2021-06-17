package com.example.mytinynasa.ui.apod

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mytinynasa.R
import com.example.mytinynasa.network.ApiClient
import com.example.mytinynasa.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApodFragment : Fragment() {

    companion object {
        fun newInstance() = ApodFragment()
    }

    private lateinit var viewModel: ApodViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.apod_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ApodViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = "";

        val eonetRecyclerView: RecyclerView = requireView().findViewById(R.id.eonet_recycler)
        eonetRecyclerView.layoutManager = LinearLayoutManager(context)
        eonetRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )

        // RETROFIT
        val retrofit = ApiClient.getApiClient("https://eonet.sci.gsfc.nasa.gov/api/v3/")
        val service = retrofit.create(ApiInterface::class.java)

        val callback: Callback<EonetResult> = object : Callback<EonetResult> {
            override fun onResponse(call: Call<EonetResult>, response: Response<EonetResult>) {
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        Log.d("MyTinyNasa", "EONET API Success : " + body.toString())

                        data = body.events as ArrayList<Event>
                        eonetRecyclerView.adapter = EonetAdapter(data)
                    }
                } else {
                    Log.d("MyTinyNasa", "EONET API Error : " + response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<EonetResult>, t: Throwable) {
                Log.d("MyTinyNasa", "EONET API Error : " + t.message)
            }

        }
        service.getEonetEvents(null, null, null, 25, null, null, null, null, null, null, null)
            .enqueue(callback)
    }

}
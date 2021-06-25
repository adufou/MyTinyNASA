package com.example.mytinynasa.mars_rover.ui

import android.app.DatePickerDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytinynasa.R
import com.example.mytinynasa.mars_rover.data.MarsRoverResult
import com.example.mytinynasa.network.ApiClient
import com.example.mytinynasa.network.ApiInterface
import com.google.android.material.slider.RangeSlider
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
    var settings: SharedPreferences? = null// = context?.getSharedPreferences("EONET", 0)!!
    var settings_editor: SharedPreferences.Editor? = null// = settings.edit()
    lateinit var progress: ProgressBar


    var cal = Calendar.getInstance()

    lateinit var rover: String
    var camera: String? = null
    var sol: String = "10"
    var date: String? = null


    val dateSetListener =
        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            var month = ""
            var day = ""
            if (monthOfYear < 10)
                month = "0$monthOfYear"
            else
                month = monthOfYear.toString()
            if (dayOfMonth < 10)
                day = "0$dayOfMonth"
            else
                day = dayOfMonth.toString()
            settings_editor?.putString("date", "${year.toString()}-$month-$day");
            settings_editor?.commit()
            getMarsPhotos()
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        //this.progress = requireView().findViewById(R.id.eonet_progress_bar)
        settings = context?.getSharedPreferences("MARS", 0)!!
        settings_editor = settings!!.edit()

        rover = settings!!.getString("rover", "Curiosity").toString()
        camera = settings!!.getString("camera", null)
        sol = settings!!.getString("sol", "10").toString()
        date = settings!!.getString("date", null)
        if (camera != null)
            camera = camera.toString()
        if (date != null)
            date = date.toString()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mars_rover, container, false)
    }

    private fun getMarsPhotos() {
        progress.isVisible = true
        val recyclerView: RecyclerView =
            requireView().findViewById(R.id.mars_rover_recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(DividerItemDecoration(context,
            LinearLayoutManager.VERTICAL))
        Log.d("Camera : ", "[MARS PHOTO] camera : " + settings?.getString("camera", ""))
        val retrofit = ApiClient.getApiClient("https://api.nasa.gov/")

        val service = retrofit.create(ApiInterface::class.java)

        var results: MarsRoverResult

//        this.progress.isVisible = true;

        val callback: Callback<MarsRoverResult> = object : Callback<MarsRoverResult> {
            override fun onFailure(call: Call<MarsRoverResult>, t: Throwable) {
                Log.d("MyTinyNasa", "API Error " + t.message)
            }

            override fun onResponse(
                call: Call<MarsRoverResult>,
                response: Response<MarsRoverResult>,
            ) {

                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        results = MarsRoverResult(body.photos);
                    }
                    progress.isVisible = false;
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

        service.getMarsPhotos(rover, camera, ApiClient.apiKey, newDate, sol.split('.')[0].toInt())
            .enqueue(callback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progress = requireView().findViewById(R.id.mars_progress_bar)
        progress.isVisible = true

        val marsRoverRecyclerView: RecyclerView =
            requireView().findViewById(R.id.mars_rover_recycler)
        marsRoverRecyclerView.layoutManager = LinearLayoutManager(context)
        marsRoverRecyclerView.addItemDecoration(DividerItemDecoration(context,
            LinearLayoutManager.VERTICAL))

        // RETROFIT
        getMarsPhotos()

        // BUTTON HANDLING
        val filter_rover_button: Button =
            requireView().findViewById(R.id.mars_rover_rover_selection_button)
        filter_rover_button.setOnClickListener {
            val rover_filter_fragment = MarsRoverSelectionFragment();
            val transaction: FragmentTransaction =
                parentFragmentManager.beginTransaction()
                    .replace(R.id.mars_rover_rover, rover_filter_fragment)
                    .addToBackStack(null)
                    .setReorderingAllowed(true)
            transaction.commit()
        }

        val filter_camera_button: Button =
            requireView().findViewById(R.id.mars_rover_camera_selection_button)
        filter_camera_button.setOnClickListener {
            val camera_filter_fragment = MarsRoverCameraSelection();
            val transaction: FragmentTransaction =
                parentFragmentManager.beginTransaction()
                    .replace(R.id.mars_rover_rover, camera_filter_fragment)
                    .addToBackStack(null)
                    .setReorderingAllowed(true)
            transaction.commit()
        }

        val filter_sol_slider: RangeSlider = requireView().findViewById(R.id.mars_rover_sol_slider)
        filter_sol_slider.addOnChangeListener { rangeSlider, value, fromUser ->
            // Responds to when slider's value is changed
            settings_editor!!.putString("sol", value.toString())
            settings_editor!!.commit()
            getMarsPhotos()
        }

        val filter_date_button: Button =
            requireView().findViewById(R.id.mars_rover_date_selection_button)
        filter_date_button.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(
                requireContext(),
                android.R.style.Theme_Material_Dialog_NoActionBar,
                dateSetListener,
                year, month,
                day
            ).show()
        }
    }
}
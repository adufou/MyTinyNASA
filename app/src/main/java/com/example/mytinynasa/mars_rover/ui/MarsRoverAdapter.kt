package com.example.mytinynasa.mars_rover.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mytinynasa.R
import com.example.mytinynasa.mars_rover.data.MarsRoverModel

class MarsRoverAdapter(val data : List<MarsRoverModel>) : RecyclerView.Adapter<MarsRoverAdapter.MarsRoverHolder>() {
    class MarsRoverHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(model : MarsRoverModel) {
            val imgSrcImageView = itemView.findViewById<ImageView>(R.id.mars_item_image)
            val dateTextView : TextView = itemView.findViewById(R.id.mars_item_date)
            val cameraInfoTextView : TextView = itemView.findViewById(R.id.mars_item_cameraInfo)
            val roverTextView : TextView = itemView.findViewById(R.id.mars_item_rover)
            Glide.with(itemView.context).load(model.img_src).centerCrop().into(imgSrcImageView)
            dateTextView.text = model.earth_date
            roverTextView.text = model.rover.name
            cameraInfoTextView.text = model.camera.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsRoverHolder {

        val viewHolder : MarsRoverHolder
        val rowView : View = LayoutInflater.from(parent.context).inflate(R.layout.mars_rover_item_list, parent, false)
        viewHolder = MarsRoverHolder(rowView)

        return viewHolder
    }

    override fun onBindViewHolder(holder: MarsRoverHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.tag = position
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
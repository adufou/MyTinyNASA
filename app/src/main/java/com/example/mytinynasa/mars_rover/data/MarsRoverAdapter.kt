package com.example.mytinynasa.mars_rover.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mytinynasa.R

class MarsRoverAdapter(val data : List<MarsRoverModel>, val onItemClickListener : View.OnClickListener) : RecyclerView.Adapter<MarsRoverAdapter.MarsRoverHolder>() {
    class MarsRoverHolder (itemWiew: View) : RecyclerView.ViewHolder(itemWiew) {
        //val imgSrcImageView : ImageView = itemWiew.findViewById(R.id.)
        val dateTextView : TextView = itemWiew.findViewById(R.id.item_date)
        val cameraInfoTextView : TextView = itemWiew.findViewById(R.id.item_cameraInfo)
        val roverTextView : TextView = itemWiew.findViewById(R.id.item_rover)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsRoverHolder {

        val viewHolder : MarsRoverHolder
        val rowView : View = LayoutInflater.from(parent.context).inflate(R.layout.mars_rover_item_list, parent, false)
        rowView.setOnClickListener(onItemClickListener)
        viewHolder = MarsRoverHolder(rowView)

        return viewHolder
    }

    override fun onBindViewHolder(holder: MarsRoverHolder, position: Int) {
        holder.dateTextView.text = data[position].earth_date
        holder.roverTextView.text = data[position].roverType
        holder.cameraInfoTextView.text = data[position].cameraInfo
        holder.itemView.tag = position
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
package com.example.mytinynasa.eonet.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mytinynasa.R
import com.example.mytinynasa.eonet.data.Event

class EonetAdapter(private val data : List<Event>) : RecyclerView.Adapter<EonetAdapter.EonetHolder>() {
    class EonetHolder(rowView : View) : ViewHolder(rowView) {
        val titleTextView : TextView = rowView.findViewById(R.id.eonet_item_title)
        val descriptionTextView : TextView = rowView.findViewById(R.id.eonet_item_description)
        //val linkTextView : TextView = rowView.findViewById(R.id.eonet_item_link)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EonetHolder {
        val viewHolder : EonetHolder
        val rowView : View = LayoutInflater.from(parent.context).inflate(R.layout.eonet_list_item, parent, false)

        viewHolder = EonetHolder(rowView)

        return viewHolder
    }

    override fun onBindViewHolder(holder: EonetHolder, position: Int) {
        holder.titleTextView.text = data[position].title
        holder.descriptionTextView.text = data[position].description
        //holder.linkTextView.text = data[position].link

        holder.itemView.tag = position
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
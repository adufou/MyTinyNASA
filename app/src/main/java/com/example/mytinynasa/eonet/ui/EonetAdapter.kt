package com.example.mytinynasa.eonet.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mytinynasa.R
import com.example.mytinynasa.eonet.data.Event
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class EonetAdapter(private val data : List<Event>) : RecyclerView.Adapter<EonetAdapter.EonetHolder>() {
    class EonetHolder(rowView : View) : ViewHolder(rowView) {
        val titleTextView : TextView = rowView.findViewById(R.id.eonet_item_title)

        val descriptionTitleTextView : TextView = rowView.findViewById(R.id.eonet_item_description_title)
        val descriptionTextView : TextView = rowView.findViewById(R.id.eonet_item_description)

        val typeTitleTextView : TextView = rowView.findViewById(R.id.eonet_item_type_title)
        val typeTextView : TextView = rowView.findViewById(R.id.eonet_item_type)

        val dateTitleTextView : TextView = rowView.findViewById(R.id.eonet_item_date_title)
        val dateTextView : TextView = rowView.findViewById(R.id.eonet_item_date)

        val magnitudeTitleTextView : TextView = rowView.findViewById(R.id.eonet_item_magnitude_title)
        val magnitudeTextView : TextView = rowView.findViewById(R.id.eonet_item_magnitude)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EonetHolder {
        val viewHolder : EonetHolder
        val rowView : View = LayoutInflater.from(parent.context).inflate(R.layout.eonet_list_item, parent, false)

        viewHolder = EonetHolder(rowView)

        return viewHolder
    }

    override fun onBindViewHolder(holder: EonetHolder, position: Int) {
        holder.titleTextView.text = data[position].title

        holder.descriptionTitleTextView.isVisible = true
        holder.descriptionTextView.isVisible = true

        data[position].description?.let {
            holder.descriptionTitleTextView.isVisible = false
            holder.descriptionTextView.isVisible = false
        }

        holder.descriptionTitleTextView.text = data[position].description
        holder.descriptionTextView.text = data[position].description

        var str : String = ""
        data[position].categories?.let { categories ->
            for (category in categories) {
                if (str == "")
                    str = str.plus(category.title)
                else
                    str = str.plus(" | ").plus(category.title)
            }
        }
        holder.typeTextView.text = str

        val df_in = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        val df_out = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", Locale.US)

        holder.dateTextView.text = null
        holder.magnitudeTextView.text = null

        data[position].geometry?.get(0)?.let { geometry ->
            geometry.date?.let { date_str ->
                df_in.parse(date_str)?.let { date ->
                    val date_displayed = df_out.format(date)
                    holder.dateTextView.text = date_displayed
                }
            }

            var magnitude_displayed : String = ""
            geometry.magnitudeValue?.let { magVal ->
                magnitude_displayed = magVal.toString()
            }

            geometry.magnitudeUnit?.let{ magUnit ->
                magnitude_displayed = magnitude_displayed.plus(' ').plus(magUnit)
            }

            if (magnitude_displayed != "")
                holder.magnitudeTextView.text = magnitude_displayed
        }


        holder.itemView.tag = position
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
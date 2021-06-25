package com.example.mytinynasa.apod.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mytinynasa.R
import com.example.mytinynasa.apod.data.ApodModel

class ApodAdapter(val data : List<ApodModel>) : RecyclerView.Adapter<ApodAdapter.ApodHolder>() {
    class ApodHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(model : ApodModel) {
            val imgSrcImageView = itemView.findViewById<ImageView>(R.id.apod_item_image)
            val titleTextView : TextView = itemView.findViewById(R.id.apod_title)
            val explanationTextView : TextView = itemView.findViewById(R.id.apod_explanation)
            val authorTextView : TextView = itemView.findViewById(R.id.apod_author)
            if (model.media_type == "image")
                Glide.with(itemView.context).load(model.url).centerCrop().into(imgSrcImageView)
            else
                Glide.with(itemView.context).load("https://file1.science-et-vie.com/var/scienceetvie/storage/images/8/6/86422/histoire-une-nuit-sans-fin.jpg?alias=original").override(1024, 768).centerCrop().into(imgSrcImageView)
            titleTextView.text = model.title
            explanationTextView.text = model.explanation.splitToSequence(".","?").first()
            authorTextView.text = model.copyright
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApodHolder {

        val viewHolder : ApodHolder
        val rowView : View = LayoutInflater.from(parent.context).inflate(R.layout.apod_item_list, parent, false)
        viewHolder = ApodHolder(rowView)

        return viewHolder
    }

    override fun onBindViewHolder(holder: ApodHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.tag = position
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
package com.dogbreedexplorer.ui.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dogbreedexplorer.R
import com.dogbreedexplorer.ui.model.Breed

class FeedAdapter(private val list: List<Breed>) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)

        return FeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeedAdapter.FeedViewHolder, position: Int) {
        val itemsViewModel = list[position]

        holder.name.text = itemsViewModel.name
        holder.origin.text = itemsViewModel.origin
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class FeedViewHolder(item: View) : RecyclerView.ViewHolder(item){
        val name : TextView = itemView.findViewById(R.id.tvBreed)
        val origin: TextView = itemView.findViewById(R.id.tvOrigin)
    }

}
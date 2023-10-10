package com.dogbreedexplorer.ui.activities

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dogbreedexplorer.R
import com.dogbreedexplorer.ui.model.Breed

class FeedAdapter(val activity: Activity) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    private var breedList: List<Breed>? = null

    fun setAllBreeds(breedList: List<Breed>){
        this.breedList = breedList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)

        return FeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeedAdapter.FeedViewHolder, position: Int) {
        holder.bind(breedList?.get(position)!!, activity)
    }

    override fun getItemCount(): Int {
        if(breedList == null) return 0
        else return breedList?.size!!
    }

    class FeedViewHolder(item: View) : RecyclerView.ViewHolder(item){
        val name : TextView = itemView.findViewById(R.id.tvBreed)
        val origin: TextView = itemView.findViewById(R.id.tvOrigin)

        fun bind(data: Breed, activity: Activity){
            name.text = data.name
            origin.text = data.origin
        }
    }

}
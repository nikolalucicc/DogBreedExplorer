package com.dogbreedexplorer.ui.breeds

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.dogbreedexplorer.R
import com.dogbreedexplorer.ui.breedDetails.BreedDetailsFragment
import com.dogbreedexplorer.ui.model.Breed

class BreedsAdapter(val activity: Activity) : RecyclerView.Adapter<BreedsAdapter.FeedViewHolder>() {

    private var breedList: List<Breed>? = null

    fun setAllBreeds(breedList: List<Breed>){
        this.breedList = breedList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)

        return FeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.bind(breedList?.get(position)!!, activity)

        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val item: Breed = breedList!![position]
                val activity = v!!.context as AppCompatActivity

                // Kreirajte instancu fragmenta pomoću newInstance metode
                val idInt = item.id?.toIntOrNull() ?: -1
                val details = BreedDetailsFragment.newInstance(idInt)

                if (details != null) {
                    activity.supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment, details)
                        .addToBackStack(null)
                        .commit()
                }

            }
        })
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
package com.dogbreedexplorer.ui.breeds

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.dogbreedexplorer.R
import com.dogbreedexplorer.ui.breedDetails.BreedDetailsFragment
import com.dogbreedexplorer.ui.model.Breed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BreedsAdapter(
    val activity: Activity,
    private val viewModel: MainViewModel,
    private var onShareClickListener: (Breed) -> Unit
) : RecyclerView.Adapter<BreedsAdapter.FeedViewHolder>() {

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

        val breed = breedList?.get(position)
        holder.shareButton.setOnClickListener{
            if (breed != null) {
                onShareClickListener.invoke(breed)
            }
        }

        holder.voteButton.setOnClickListener {
            if (breed != null) {
                CoroutineScope(Dispatchers.Main).launch {
                    breed.reference_image_id?.let { it1 -> viewModel.sendVote(it1, null, 1) }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        if(breedList == null) return 0
        else return breedList?.size!!
    }

    class FeedViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val name: TextView = itemView.findViewById(R.id.tvBreed)
        val origin: TextView = itemView.findViewById(R.id.tvOrigin)
        val shareButton: ImageButton = itemView.findViewById(R.id.share)
        val voteButton: ImageButton = itemView.findViewById(R.id.vote)
        val breedImage: ImageView = itemView.findViewById(R.id.ivBreedImage)
        val numberOfFavorites: TextView = itemView.findViewById(R.id.tvNumberOfFavorites)

        fun bind(data: Breed, activity: Activity) {
            name.text = data.name
            origin.text = data.origin

            if (!data.reference_image_id.isNullOrBlank()) {
                breedImage.visibility = View.VISIBLE
            } else {
                breedImage.visibility = View.INVISIBLE
            }
        }
    }


}
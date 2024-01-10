package com.dogbreedexplorer.ui.favorite

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dogbreedexplorer.R
import com.dogbreedexplorer.ui.breedDetails.BreedDetailsFragment
import com.dogbreedexplorer.ui.model.Breed
import com.dogbreedexplorer.utils.model.Favorite

class FavoriteAdapter(
    val activity: Activity,
    private val viewModel: FavouriteViewModel,
    private var onShareClickListener: (Breed) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavViewHolder>() {

    private var favList: List<Favorite>? = null
    private var breedList: List<Breed>? = null

    fun setFavBreeds(favList: List<Favorite>, breedList: List<Breed>) {
        this.favList = favList
        this.breedList = breedList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fav_item, parent, false)
        return FavViewHolder(view)
    }

    override fun getItemCount(): Int = favList?.size ?: 0

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        holder.bind(favList?.get(position)!!, breedList ?: emptyList())

        holder.itemView.setOnClickListener {
            val item: Breed = breedList!![position]
            val activity = it.context as AppCompatActivity

            val idInt = item.id?.toIntOrNull() ?: -1
            val details = BreedDetailsFragment.newInstance(idInt)

            if (details != null) {
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment, details)
                    .addToBackStack(null)
                    .commit()
            }
        }

        val breed = breedList?.get(position)
        holder.shareButton.setOnClickListener {
            breed?.let { onShareClickListener.invoke(it) }
        }
    }

    class FavViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val name: TextView = itemView.findViewById(R.id.tvFavBreed)
        val origin: TextView = itemView.findViewById(R.id.tvFavOrigin)
        val shareButton: ImageButton = itemView.findViewById(R.id.favShare)
        val favouriteButton: ImageButton = itemView.findViewById(R.id.btnFav)
        val breedImage: ImageView = itemView.findViewById(R.id.ivFavBreedImage)

        var isVoted: Boolean = false

        fun bind(data: Favorite, breedList: List<Breed>) {
            val favImageId = data.image_id
            val matchingBreed = getBreedByImageId(favImageId, breedList)

            if (matchingBreed != null) {
                val breedName = matchingBreed.name
                val breedOrigin = matchingBreed.origin

                name.text = breedName
                origin.text = breedOrigin
            }

            if (!matchingBreed?.reference_image_id.isNullOrBlank()) {
                breedImage.visibility = View.VISIBLE
            } else {
                breedImage.visibility = View.INVISIBLE
            }
        }

        private fun getBreedByImageId(imageId: String, breedList: List<Breed>): Breed? {
            for (breed in breedList) {
                if (breed.reference_image_id == imageId) {
                    return breed
                }
            }
            return null
        }
    }
}

package com.dogbreedexplorer.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dogbreedexplorer.R
import com.dogbreedexplorer.ui.model.Breed
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {
    private lateinit var adapter: FavoriteAdapter
    private val viewModel: FavouriteViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerview: RecyclerView = view.findViewById(R.id.rvFavFeed)
        recyclerview.layoutManager = LinearLayoutManager(context)
        recyclerview.isNestedScrollingEnabled

        adapter = FavoriteAdapter(requireActivity(), viewModel) {breed ->
            shareBreed(breed)
        }
        recyclerview.adapter = adapter

        initViewModel()
    }

    private fun initViewModel(){
        lifecycleScope.launch {
            viewModel.data.collect{state ->
                when (state){
                    is FavoriteState.Loading -> {
                        Log.d("viewModel", "Loading...")
                    }
                    is FavoriteState.Success -> {
                        adapter.setFavBreeds(state.favBreed, state.breedList)
                        adapter.notifyDataSetChanged()
                        Log.d("ViewModel", "Success: ${state.favBreed}")
                    }
                    is FavoriteState.Error -> {
                        val errorMessage = "Error getting list of favorites: ${state.message}"
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
                        Log.e("ViewModel", errorMessage)
                    }
                }

            }
        }
        viewModel.getFavorites(requireContext())
    }

    private fun shareBreed(breed: Breed) {
        val externalApiUrl = "Check out this breed: ${breed.name} https://api.thedogapi.com/v1/breeds/${breed.id}"

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, externalApiUrl)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}
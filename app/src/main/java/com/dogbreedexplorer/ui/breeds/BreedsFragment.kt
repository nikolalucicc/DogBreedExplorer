package com.dogbreedexplorer.ui.breeds

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dogbreedexplorer.R
import com.dogbreedexplorer.ui.login.LoginViewModel
import com.dogbreedexplorer.ui.model.Breed
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class BreedsFragment : Fragment() {
    private lateinit var adapter: BreedsAdapter

    private val viewModel: MainViewModel by viewModel()
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_home, container, false)

        val appBarLayout = view.findViewById<CollapsingToolbarLayout>(R.id.appBarLayout)
        val params = appBarLayout.layoutParams as CoordinatorLayout.LayoutParams
        params.anchorGravity = 0
        appBarLayout.layoutParams = params

        val buttonSearch = view.findViewById<ImageButton>(R.id.search)
        buttonSearch.setOnClickListener{
            findNavController().navigate(R.id.action_home_fragment_to_search_fragment)
        }

        val buttonLiked = view.findViewById<ImageButton>(R.id.liked)
        buttonLiked.setOnClickListener{
            findNavController().navigate(R.id.action_home_fragment_to_liked_fragment)
        }

        loginViewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        val token = loginViewModel.token
        Log.d("AuthToken", "Authentication Token in breeds fragment: $token")

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerview: RecyclerView = view.findViewById(R.id.rvFeed)
        recyclerview.layoutManager = LinearLayoutManager(context)
        recyclerview.isNestedScrollingEnabled()

        adapter = BreedsAdapter(requireActivity()) {breed ->
            shareBreed(breed)
        }
        recyclerview.adapter = adapter

        initViewModel()
    }

    private fun initViewModel() {
        lifecycleScope.launch {
            viewModel.data.collect { state ->
                when (state) {
                    is BreedState.Loading -> {
                        // Show loading indicator or perform UI updates for loading state
                        Log.d("ViewModel", "Loading...")
                    }
                    is BreedState.Success -> {
                        adapter.setAllBreeds(state.breeds)
                        adapter.notifyDataSetChanged()
                        Log.d("ViewModel", "Success: ${state.breeds}")
                    }
                    is BreedState.Error -> {
                        val errorMessage = "Error getting list of breeds: ${state.message}"
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
                        Log.e("ViewModel", errorMessage)
                    }
                }
            }
        }
        viewModel.getAllBreeds(requireContext())
    }

    private fun shareBreed(breed: Breed) {
        val deepLinkUrl = "Check out this breed: ${breed.name} https://api.thedogapi.com/v1/breeds/${breed.id}"

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, deepLinkUrl)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}
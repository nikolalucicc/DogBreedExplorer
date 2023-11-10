package com.dogbreedexplorer.ui.breeds

import android.os.Bundle
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
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class BreedsFragment : Fragment() {
    private lateinit var adapter: BreedsAdapter

    private val viewModel: MainViewModel by viewModel()

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

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerview: RecyclerView = view.findViewById(R.id.rvFeed)
        recyclerview.layoutManager = LinearLayoutManager(context)
        recyclerview.isNestedScrollingEnabled()

        adapter = BreedsAdapter(requireActivity())
        recyclerview.adapter = adapter

        initViewModel()
    }

    private fun initViewModel() {
        lifecycleScope.launch {
            viewModel.data.collect { state ->
                when (state) {
                    is BreedState.Loading -> {
                        // Show loading indicator or perform UI updates for loading state
                    }
                    is BreedState.Success -> {
                        adapter.setAllBreeds(state.breeds)
                        adapter.notifyDataSetChanged()
                    }
                    is BreedState.Error -> {
                        Toast.makeText(requireContext(), "Error getting list of breeds: ${state.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        viewModel.getAllBreeds()
    }


}
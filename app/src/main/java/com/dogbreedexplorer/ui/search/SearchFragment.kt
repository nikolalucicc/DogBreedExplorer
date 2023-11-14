package com.dogbreedexplorer.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dogbreedexplorer.R
import com.dogbreedexplorer.ui.breeds.BreedState
import com.dogbreedexplorer.ui.breeds.BreedsAdapter
import com.dogbreedexplorer.ui.breeds.MainViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {
    private lateinit var adapter: BreedsAdapter
    private val viewModel: SearchViewModel by viewModel()
    private lateinit var q: String
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchView: androidx.appcompat.widget.SearchView = view.findViewById(R.id.search_bar)
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    q = query.trim()
                    search(q)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle text changes if needed
                return true
            }
        })

        val recyclerview: RecyclerView = view.findViewById(R.id.rvSearchFeed)
        recyclerview.layoutManager = LinearLayoutManager(context)
        recyclerview.isNestedScrollingEnabled()

        adapter = BreedsAdapter(requireActivity())
        recyclerview.adapter = adapter
    }

    private fun search(q: String) {
        lifecycleScope.launch {
            viewModel.data.collect{state ->
                when (state) {
                    is SearchState.Loading -> {
                        // Show loading indicator or perform UI updates for loading state
                    }
                    is SearchState.Success -> {
                        adapter.setAllBreeds(state.searchBreed)
                        adapter.notifyDataSetChanged()
                    }
                    is SearchState.Error -> {
                        Toast.makeText(requireContext(), "Error getting list of breeds: ${state.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        viewModel.searchBreed(q)
    }
}
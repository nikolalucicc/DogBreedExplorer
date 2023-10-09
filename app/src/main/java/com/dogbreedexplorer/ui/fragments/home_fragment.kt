package com.dogbreedexplorer.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import com.dogbreedexplorer.R

class home_fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)
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

}
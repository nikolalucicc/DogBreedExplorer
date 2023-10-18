package com.dogbreedexplorer.ui.breedDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dogbreedexplorer.R


class BreedDetailsFragment(id: Int?) : Fragment() {

    private lateinit var name: TextView
    private lateinit var origin: TextView
    private var id: Int = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_breed_details, container, false)

        name = view.findViewById(R.id.tvBreedNameDetails)
        origin = view.findViewById(R.id.tvBreedOriginDetails)

        // Pročitajte id rase iz argumenta fragmenta, ako je dostupan
        arguments?.let {
            id = it.getInt(ARG_BREED_ID, -1)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (id != -1) {
            loadDetails(id)
        } else {
            Toast.makeText(requireContext(), "Invalid breed ID", Toast.LENGTH_LONG).show()
        }
    }

    private fun loadDetails(id: Int) {
        val viewModel: DetailsViewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        viewModel.getDataObserver().observe(viewLifecycleOwner, Observer { breedDetails ->
            if (breedDetails != null) {
                name.text = breedDetails.name
                origin.text = breedDetails.origin
            } else {
                Toast.makeText(requireContext(), "Error getting details", Toast.LENGTH_LONG).show()
            }
        })

        viewModel.getDetails(id)
    }

    companion object {
        private const val ARG_BREED_ID = "breed_id"

        fun newInstance(id: Int): BreedDetailsFragment {
            val fragment = BreedDetailsFragment(id)
            val args = Bundle()
            args.putInt(ARG_BREED_ID, id)
            fragment.arguments = args
            return fragment
        }
    }
}
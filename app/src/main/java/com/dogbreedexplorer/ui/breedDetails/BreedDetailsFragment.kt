package com.dogbreedexplorer.ui.breedDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.dogbreedexplorer.R
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class BreedDetailsFragment(id: Int?) : Fragment() {

    private lateinit var name: TextView
    private lateinit var origin: TextView
    private lateinit var breed_for: TextView
    private lateinit var breed_group: TextView
    private lateinit var life_span: TextView
    private lateinit var temperament: TextView

    private lateinit var tvName: TextView
    private lateinit var tvOrigin: TextView
    private lateinit var tvBreed_for: TextView
    private lateinit var tvBreed_group: TextView
    private lateinit var tvLife_span: TextView
    private lateinit var tvTemperament: TextView

    private var id: Int = -1

    private val viewModel: DetailsViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_breed_details, container, false)

        name = view.findViewById(R.id.tvBreedNameDetails)
        origin = view.findViewById(R.id.tvBreedOriginDetails)
        breed_for = view.findViewById(R.id.tvBreedForDetails)
        breed_group = view.findViewById(R.id.tvBreedGroupDetails)
        life_span = view.findViewById(R.id.tvLifeSpanDetails)
        temperament = view.findViewById(R.id.tvTemperamentDetails)

        tvName = view.findViewById(R.id.tvBreedName)
        tvOrigin = view.findViewById(R.id.tvOrigin)
        tvBreed_for = view.findViewById(R.id.tvBreedFor)
        tvBreed_group = view.findViewById(R.id.tvBreedGroup)
        tvLife_span = view.findViewById(R.id.tvLifeSpan)
        tvTemperament = view.findViewById(R.id.tvTemperament)

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
        lifecycleScope.launch {
            viewModel.getDataObserver().collect { state ->
                when (state) {
                    is BreedDetailsState.Loading -> {
                        // Show loading indicator or perform UI updates for loading state
                    }
                    is BreedDetailsState.Success -> {
                        val breedDetails = state.breedDetails
                        if (breedDetails != null) {
                            name.text = breedDetails.name
                            origin.text = breedDetails.origin
                            breed_for.text = breedDetails.breed_for
                            breed_group.text = breedDetails.breed_group
                            life_span.text = breedDetails.life_span
                            temperament.text = breedDetails.temperament

                            val referenceImageId = breedDetails?.reference_image_id
                            val breedImage = view?.findViewById<ImageView>(R.id.breedImage)
                            val imageUrl = "https://cdn2.thedogapi.com/images/$referenceImageId.jpg"
                            if (breedImage != null) {
                                Glide.with(requireContext())
                                    .load(imageUrl)
                                    .into(breedImage)
                            }
                            tvName.visibility = if (breedDetails.name != null) View.VISIBLE else View.GONE
                            tvOrigin.visibility = if (breedDetails.origin != null) View.VISIBLE else View.GONE
                            tvBreed_for.visibility = if (breedDetails.breed_for != null) View.VISIBLE else View.GONE
                            tvBreed_group.visibility = if (breedDetails.breed_group != null) View.VISIBLE else View.GONE
                            tvLife_span.visibility = if (breedDetails.life_span != null) View.VISIBLE else View.GONE
                            tvTemperament.visibility = if (breedDetails.temperament != null) View.VISIBLE else View.GONE
                        }
                    }
                    is BreedDetailsState.Error -> {
                        Toast.makeText(requireContext(), "Error getting details: ${state.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

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
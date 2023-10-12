package com.dogbreedexplorer.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dogbreedexplorer.R
import com.dogbreedexplorer.ui.breeds.BreedsAdapter
import com.dogbreedexplorer.ui.breeds.MainViewModel

class ContainerActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var adapter: BreedsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_layout)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController

//        val recyclerview = findViewById<RecyclerView>(R.id.rvFeed)
//        recyclerview.layoutManager = LinearLayoutManager(this)
//        recyclerview.isNestedScrollingEnabled()
//
//        adapter = BreedsAdapter(this)
//        recyclerview.adapter = adapter
//
//        initViewModel()
    }

//    private fun initViewModel() {
//        val viewModel: MainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
//        viewModel.getDataObserver().observe(this, Observer{
//            if(it != null){
//                adapter.setAllBreeds(it)
//                adapter.notifyDataSetChanged()
//            } else{
//                Toast.makeText(this, "Error getting list of breeds", Toast.LENGTH_LONG).show()
//            }
//        })
//        viewModel.getAllBreeds()
//    }
}

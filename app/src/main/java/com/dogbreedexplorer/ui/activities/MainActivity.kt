package com.dogbreedexplorer.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dogbreedexplorer.R
import com.dogbreedexplorer.ui.model.Breed

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_layout)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController

        val recyclerview = findViewById<RecyclerView>(R.id.rvFeed)
        recyclerview.layoutManager = LinearLayoutManager(this)

        val data = ArrayList<Breed>()
            data.add(Breed("0", "Bigl", "Engleska"))
            data.add(Breed("1", "Sarplaninac", "Srbija"))
            data.add(Breed("2", "SOP", "Srbija"))
            data.add(Breed("3", "Doberman", "Nemacka"))


        val adapter = FeedAdapter(data)
        recyclerview.adapter = adapter
    }
}

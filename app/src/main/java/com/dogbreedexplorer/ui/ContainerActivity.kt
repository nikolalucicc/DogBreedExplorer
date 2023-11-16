package com.dogbreedexplorer.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.dogbreedexplorer.R
import com.dogbreedexplorer.ui.breedDetails.BreedDetailsFragment

class ContainerActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_layout)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController

        if (Intent.ACTION_VIEW == intent.action && intent.data != null) {
            handleDeepLink(intent.data!!)
        }
    }

    private fun handleDeepLink(deepLink: Uri) {
        Log.d("DeepLink", "Received deep link: $deepLink")

        val breedId = deepLink.lastPathSegment?.toIntOrNull()

        if (breedId != null) {
            Log.d("DeepLink", "Extracted breed ID: $breedId")

            val fragment = BreedDetailsFragment.newInstance(breedId)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, fragment)
                .commit()
        } else {
            Log.e("DeepLink", "Invalid deep link")
            Toast.makeText(this, "Invalid deep link", Toast.LENGTH_LONG).show()
        }
    }
}

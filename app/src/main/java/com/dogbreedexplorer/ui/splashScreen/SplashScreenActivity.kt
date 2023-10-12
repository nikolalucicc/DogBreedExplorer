package com.dogbreedexplorer.ui.splashScreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import com.dogbreedexplorer.R
import com.dogbreedexplorer.ui.ContainerActivity

class SplashScreenActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen_layout)

        val delayMillis: Long = 2000
        Handler().postDelayed({
            val intent = Intent(this, ContainerActivity::class.java)
            startActivity(intent)
            finish()
        }, delayMillis)
    }
}
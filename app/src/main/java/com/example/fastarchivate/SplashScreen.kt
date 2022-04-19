package com.example.fastarchivate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.example.fastarchivate.MainActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firstRun()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun firstRun() {
        if (MyPreferences.Run(this).isFirstRun()) MyPreferences.Run(this).saveRun(false)
    }
}
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

    //хз не помню зачем я сохранял первый запуск
    //ну я чекнул я нигде его не юзал так что можешь нажать во всех .kt файлах Ctrl+A -> Del
    private fun firstRun() {
        if (MyPreferences(this).run) MyPreferences(this).run = false
    }
}
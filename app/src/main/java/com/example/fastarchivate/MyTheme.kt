package com.example.fastarchivate

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

class MyTheme {
    companion object{
        fun setTheme(context: Context){
            val prefs = MyPreferences.Settings(context)

            when(prefs.getThemeId()){
                R.id.rbAutoTheme -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                R.id.rbDarkTheme -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                R.id.rbLightTheme -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}
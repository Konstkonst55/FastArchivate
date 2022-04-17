package com.example.fastarchivate

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

class MyTheme {
    companion object{
        fun setTheme(context: Context){
            val prefs = context.getSharedPreferences("SETTINGS", Context.MODE_PRIVATE)

            when(prefs.getInt("THEME", R.id.rbAutoTheme)){
                R.id.rbAutoTheme -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                R.id.rbDarkTheme -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                R.id.rbLightTheme -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}
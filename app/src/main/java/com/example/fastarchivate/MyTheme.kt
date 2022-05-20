package com.example.fastarchivate

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

class MyTheme {
    companion object{
        fun setTheme(context: Context){
            //тут проверяется какая тема в настрйоках и от этого ставится тема в приложении
            when(MyPreferences(context).theme){
                R.id.rbAutoTheme -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                R.id.rbDarkTheme -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                R.id.rbLightTheme -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}
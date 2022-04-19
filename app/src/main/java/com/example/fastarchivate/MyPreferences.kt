package com.example.fastarchivate

import android.content.Context

class MyPreferences {

    class Settings(context: Context){
        private val settingsPreferences = context.getSharedPreferences("SETTINGS", Context.MODE_PRIVATE)

        fun saveInt(keyName: String, value: Int) {
            settingsPreferences.edit().putInt(keyName, value).apply()
        }

        fun getThemeId(): Int {
            return settingsPreferences.getInt("THEME", R.id.rbAutoTheme)
        }
    }

    class Run(context: Context){
        private val settingsPreferences = context.getSharedPreferences("RUN", Context.MODE_PRIVATE)

        fun saveRun(value: Boolean){
            settingsPreferences.edit().putBoolean("FIRST_RUN", value).apply()
        }

        fun isFirstRun() : Boolean{
            return settingsPreferences.getBoolean("FIRST_RUN", true)
        }
    }
}
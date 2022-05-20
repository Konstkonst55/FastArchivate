package com.example.fastarchivate

import android.content.Context

//в этом классе хранятся все настройки типо тема первый запуск и директория
class MyPreferences(context: Context) {
    private val prefs = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)

    var theme: Int
        get() = prefs.getInt(Constants.THEME_KEY, R.id.rbAutoTheme)
        set(id) {
            prefs.edit().putInt(Constants.THEME_KEY, id).apply()
        }

    var run: Boolean
        get() = prefs.getBoolean(Constants.FIRST_RUN_KEY, true)
        set(value){
            prefs.edit().putBoolean(Constants.FIRST_RUN_KEY, value).apply()
        }

    var directory: String?
    get() = prefs.getString(Constants.DIRECTORY_KEY, "/storage/emulated/0/Download/")
    set(value){
        prefs.edit().putString(Constants.DIRECTORY_KEY, value).apply()
    }
}
package com.example.fastarchivate

import android.Manifest

object Constants {
    //объект с константами
    //консты для натсроек
    const val PREFS_NAME = "SETTINGS"
    const val THEME_KEY = "THEME"
    const val FIRST_RUN_KEY = "FIRST_RUN"
    const val DIRECTORY_KEY = "DIRECTORY"
    //консты для файл пикера
    const val FILE_PICKER_CODE = 6969
    //консты для разрешений
    const val REQUEST_EXTERNAL_STORAGE = 1
    val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
}
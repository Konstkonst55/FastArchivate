package com.example.fastarchivate.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.fastarchivate.MyTheme
import com.example.fastarchivate.R
import com.example.fastarchivate.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var galleryViewModel: SettingsViewModel
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val settingsKeyName: String = "SETTINGS"
    private lateinit var prefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        galleryViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        init()

        binding.rgThemes.setOnCheckedChangeListener { _, i ->
            savePreference("THEME", i)
            MyTheme.setTheme(requireContext())
        }

        return binding.root
    }

    private fun init(){
        prefs = requireContext().getSharedPreferences(settingsKeyName, Context.MODE_PRIVATE)
        binding.rgThemes.check(getPreference("THEME"))
    }

    private fun savePreference(KEY_NAME: String, value: Int) {
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putInt(KEY_NAME, value).apply()
    }

    private fun getPreference(KEY_NAME: String) : Int{
        return prefs.getInt(KEY_NAME, R.id.rbAutoTheme)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
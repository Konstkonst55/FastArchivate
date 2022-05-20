package com.example.fastarchivate.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.fastarchivate.Constants
import com.example.fastarchivate.MyPreferences
import com.example.fastarchivate.MyTheme
import com.example.fastarchivate.R
import com.example.fastarchivate.databinding.FragmentSettingsBinding
import ru.bartwell.exfilepicker.ExFilePicker


class SettingsFragment : Fragment() {

    private lateinit var galleryViewModel: SettingsViewModel
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val exFilePicker: ExFilePicker = ExFilePicker()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        galleryViewModel = ViewModelProvider(this)[SettingsViewModel::class.java]
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        init(binding.root)

        //тема устанавливается
        binding.rgThemes.setOnCheckedChangeListener { _, i ->
            MyPreferences(requireContext()).theme = i
            MyTheme.setTheme(requireContext())
        }

        //тут запускается диалог с выбором директории с помощью библиотеки говна пизда
        binding.ibOpen.setOnClickListener{
            exFilePicker.setChoiceType(ExFilePicker.ChoiceType.DIRECTORIES)
            exFilePicker.setCanChooseOnlyOneItem(true)
            exFilePicker.start(requireActivity(), Constants.FILE_PICKER_CODE)
            init(binding.root)
        }

        return binding.root
    }

    //тут короче мне лень что то делать это метод статический который вызывается в маинактвити чтобы обновить текствью похуй
    companion object{
        fun init(view: View?){
            view?.findViewById<TextView>(R.id.tvPath)?.text = MyPreferences(view!!.context).directory
            view.findViewById<RadioGroup>(R.id.rgThemes)?.check(MyPreferences(view.context).theme)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
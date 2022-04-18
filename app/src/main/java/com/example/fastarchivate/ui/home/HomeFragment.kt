package com.example.fastarchivate.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.fastarchivate.MyTheme
import com.example.fastarchivate.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val QUERY_CODE = 6969

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        init()

        binding.bCreateArchive.setOnClickListener {
            if (binding.etName.text.toString() != ""){
                startFilePicker()
            }else{
                Snackbar.make(requireView(), "Enter the archive name", Snackbar.LENGTH_LONG).show()
            }
        }

        return binding.root
    }

    private fun init() {
        MyTheme.setTheme(requireContext())
    }

    private fun startFilePicker() {
        val intent = Intent()
            .setType("*/*")
            .setAction(Intent.ACTION_GET_CONTENT)

        startActivityForResult(Intent.createChooser(intent, "Select a file"), QUERY_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == QUERY_CODE) {
            //todo
            Snackbar.make(requireView(), data?.data?.path.toString(), Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
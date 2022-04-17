package com.example.fastarchivate.ui.home

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.fastarchivate.MyTheme
import com.example.fastarchivate.URIHelper
import com.example.fastarchivate.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        MyTheme.setTheme(requireContext())

        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            val filePath = URIHelper.getPath(requireContext(), uri!!)
            Snackbar.make(requireView(), filePath.toString(), Snackbar.LENGTH_LONG).show()
        }

        binding.bCreateArchive.setOnClickListener {
            if (binding.etName.text.toString() != ""){
                getContent.launch("file/*")
            }else{
                Snackbar.make(requireView(), "Enter the archive name", Snackbar.LENGTH_LONG).show()
            }
        }

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
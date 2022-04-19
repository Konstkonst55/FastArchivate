package com.example.fastarchivate.ui.home

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.fastarchivate.MyTheme
import com.example.fastarchivate.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import androidx.core.app.ActivityCompat

import android.content.pm.PackageManager

import android.net.Uri
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.fastarchivate.URIHelper

import com.example.fastarchivate.MyArchiver

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var newArchiveName: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        init()

        binding.bCreateArchive.setOnClickListener {
            if (binding.etName.text.toString() != ""){
                newArchiveName = binding.etName.text.toString()
                getContent.launch("*/*")
            }else{
                Snackbar.make(requireView(), "Enter the archive name", Snackbar.LENGTH_LONG).show()
            }
        }

        return binding.root
    }

    private fun init() {
        MyTheme.setTheme(requireContext())
        verifyStoragePermissions()
    }

    private val getContent = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uriList: List<Uri> ->

        MyArchiver.createZipArchive(
            fileName = getArrayUri(uriList),
            newArchiveName = newArchiveName,
            view = requireView()
        )
        Log.i("ZIP", "uri path: $uriList")
    }

    private fun getArrayUri(uriList: List<Uri>) : List<String> {
        val uriArr: ArrayList<String> = ArrayList()
        uriList.forEach { uri ->
            uriArr.add(URIHelper.getPath(requireContext(), uri).toString())
        }
        return uriArr
    }

    private fun verifyStoragePermissions() {
        val REQUEST_EXTERNAL_STORAGE = 1
        val PERMISSIONS_STORAGE = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        val permission = ActivityCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
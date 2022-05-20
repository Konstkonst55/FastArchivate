package com.example.fastarchivate.ui.home

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.fastarchivate.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import androidx.core.app.ActivityCompat

import android.content.pm.PackageManager

import android.net.Uri
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.fastarchivate.*

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class HomeFragment : Fragment(), CoroutineScope {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var newArchiveName: String
    private var type: String = "zip"

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        init()

        //архивация
        binding.bCreateArchive.setOnClickListener {
            if (binding.etName.text.toString().isNotEmpty()){
                newArchiveName = binding.etName.text.toString()
                getContent.launch("*/*")
            }else{
                Snackbar.make(requireView(), "Enter the archive name", Snackbar.LENGTH_LONG).show()
            }
        }

        //выбор расширения
        binding.rgType.setOnCheckedChangeListener{_, id: Int ->
            when(id){
                R.id.radioButton_RAR -> type = "rar"
                R.id.radioButton_ZIP -> type = "zip"
            }
            Log.i("type", type)
        }

        return binding.root
    }

    //тут тема и чекаются разреешние на сохранение в хранилище
    private fun init() {
        MyTheme.setTheme(requireContext())
        verifyStoragePermissions()
    }

    //получение списка выбранных файлов
    var getContent = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uriList: List<Uri> ->
        binding.pbLoading.visibility = View.VISIBLE
        try{
            launch { createArchiveAsync(uriList) }
            Log.i("ZIP", "uri path: $uriList")
        }catch (ex: Exception){
            Log.i("ZIP", ex.toString())
            binding.pbLoading.visibility = View.GONE
        }
    }

    //тут архив создается
    private suspend fun createArchiveAsync(uriList: List<Uri>) = coroutineScope {
        async {
            MyArchiver.createZipArchive(
                fileName = getArrayUri(uriList),
                newArchiveName = newArchiveName,
                view = requireView(),
                pb = binding.pbLoading,
                type = type
            )
        }
    }

    //получение листа со строками из листа с юри
    private fun getArrayUri(uriList: List<Uri>) : List<String> {
        val uriArr: ArrayList<String> = ArrayList()
        try {
            uriList.forEach { uri ->
                uriArr.add(URIHelper.getPath(requireContext(), uri).toString())
            }

        }catch (ex: Exception){
            Log.i("ZIP", ex.toString())
            binding.pbLoading.visibility = View.GONE
        }
        return uriArr
    }

    //проверка разрешения
    private fun verifyStoragePermissions() {



        val permission = ActivityCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                Constants.PERMISSIONS_STORAGE,
                Constants.REQUEST_EXTERNAL_STORAGE
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
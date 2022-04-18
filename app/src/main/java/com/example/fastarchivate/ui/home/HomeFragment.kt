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
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import androidx.core.app.ActivityCompat

import android.content.pm.PackageManager

import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.fastarchivate.URIHelper

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

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
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
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

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        createZipArchive(URIHelper.getPath(requireContext(), uri!!).toString())
        Log.i("ZIP", "uri path: " + URIHelper.getPath(requireContext(), uri).toString())
    }

    private fun createZipArchive(fileName: String) {
        val outPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path.toString() + "/$newArchiveName.zip"
        val files: Array<String> = arrayOf(fileName)
        val out = ZipOutputStream(BufferedOutputStream(FileOutputStream(outPath)))
        Log.i("ZIP", "out")
        for (file in files) {
            val fi = FileInputStream(file)
            val origin = BufferedInputStream(fi)
            val entry = ZipEntry(file.substring(file.lastIndexOf("/")))
            out.putNextEntry(entry)
            origin.copyTo(out, 1024)
            Log.i("ZIP", "copy_to $outPath")
            Snackbar.make(requireView(), "The archive is saved in $outPath", Snackbar.LENGTH_LONG)
                .setAction("COPY") {
                    val clipboard: ClipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText(outPath, outPath)
                    clipboard.setPrimaryClip(clip)
                    Snackbar.make(requireView(), "Copied!", Snackbar.LENGTH_LONG).show()
                }.show()
            origin.close()
        }
        out.close()
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
package com.example.fastarchivate

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.opengl.Visibility
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class MyArchiver {
    companion object{
        fun createZipArchive(fileName: List<String>, newArchiveName: String, view: View, pb: ProgressBar, type: String) {
            val outPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path.toString() + "/$newArchiveName.$type"
            val files: List<String> = fileName
            val out = ZipOutputStream(BufferedOutputStream(FileOutputStream(outPath)))
            Log.i("ZIP", "out")
            for (file in files) {
                val fi = FileInputStream(file)
                val origin = BufferedInputStream(fi)
                val entry = ZipEntry(file.substring(file.lastIndexOf("/")))
                out.putNextEntry(entry)
                origin.copyTo(out, 1024)
                Log.i("ZIP", "copy_to $outPath")
                Snackbar.make(view, "The archive is saved in $outPath", Snackbar.LENGTH_LONG)
                    .setAction("COPY") {
                        val clipboard: ClipboardManager = view.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText(outPath, outPath)
                        clipboard.setPrimaryClip(clip)
                        Snackbar.make(view, "Copied!", Snackbar.LENGTH_LONG).show()
                    }.show()
                origin.close()
            }
            out.close()
            pb.visibility = View.GONE
        }
    }
}
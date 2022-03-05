package com.example.logtech

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.documentfile.provider.DocumentFile
import com.example.logtech.databinding.ActivityFilesBinding
import java.io.File

class FilesActivity : AppCompatActivity() {

    private val REQUEST_DIR = 90
    private lateinit var binding: ActivityFilesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_files)
        binding = ActivityFilesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        startActivityForResult(intent, REQUEST_DIR)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQUEST_DIR) {
            val treeUri: Uri = data?.getData()!!
            val pickedDir = DocumentFile.fromTreeUri(this, treeUri)
            for (file in pickedDir!!.listFiles()) {
                Log.i("DR4", "Found file: ${file.name}, size: ${file.length()}")
            }
        }
    }
}
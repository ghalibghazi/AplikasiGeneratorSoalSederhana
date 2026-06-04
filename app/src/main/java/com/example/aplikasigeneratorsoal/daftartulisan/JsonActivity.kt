package com.example.aplikasigeneratorsoal.daftartulisan

import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasigeneratorsoal.R
import java.io.File

class JsonActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: JsonFileAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, true)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.daftar_tulisan)


        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val jsonFiles = getJsonFiles()
        adapter = JsonFileAdapter(jsonFiles, this)
        recyclerView.adapter = adapter
    }
//    private fun getJsonFiles(): List<JsonFile> {
//        // Specify the Documents directory
//        val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
//        val fileDir = File(directory, "text")
//        val jsonFiles = mutableListOf<JsonFile>()
//        val files = fileDir.listFiles()
//
//        // Check if files are not null and iterate through them
//        files?.forEach { file ->
//            if (file.isFile && file.name.endsWith(".json")) {
//                jsonFiles.add(JsonFile(file.name))
//            }
//        }
//        return jsonFiles
//    }

    private fun getJsonFiles(): List<JsonFile> {
        val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val fileDir = File(directory, "text")
        val jsonFiles = mutableListOf<JsonFile>()
        val files = fileDir.listFiles()

        files?.forEach { file ->
            if (file.isFile && file.name.endsWith(".json")) {
                val displayName = file.name.removeSuffix("-teks.json")
                jsonFiles.add(JsonFile(displayName))
            }
        }
        return jsonFiles
    }


}
package com.example.aplikasigeneratorsoal.quiz

import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasigeneratorsoal.R
import com.example.aplikasigeneratorsoal.daftartulisan.JsonFile
import java.io.File

class TextJsonActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: QuizJsonFileAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.siswa_daftar_tulisan)


        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val jsonFiles = getJsonFiles()
        adapter = QuizJsonFileAdapter(jsonFiles, this)
        recyclerView.adapter = adapter
    }
    private fun getJsonFiles(): List<JsonFile> {
        // Specify the Documents directory
        val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val textDirectory = File(directory, "text")
        val jsonFiles = mutableListOf<JsonFile>()
        val files = textDirectory.listFiles()

        // Check if files are not null and iterate through them
        files?.forEach { file ->
            if (file.isFile && file.name.endsWith(".json")) {
                val fileNameWithoutExtension = file.name.removeSuffix("-teks.json")
                jsonFiles.add(JsonFile(fileNameWithoutExtension))
            }
        }
        return jsonFiles
    }

}
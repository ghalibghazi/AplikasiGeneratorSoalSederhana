package com.example.aplikasigeneratorsoal.daftarsiswa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasigeneratorsoal.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SiswaActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var studentAdapter: SiswaAdapter
    private var studentList: List<Siswa> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.daftar_siswa)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadStudentsFromJson()
        studentAdapter = SiswaAdapter(studentList)
        recyclerView.adapter = studentAdapter
    }

    private fun loadStudentsFromJson() {
        val jsonString = assets.open("daftarsiswa.json").bufferedReader().use { it.readText() }
        val gson = Gson()
        val studentType = object : TypeToken<List<Siswa>>() {}.type
        studentList = gson.fromJson(jsonString, studentType)
    }
}
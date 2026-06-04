package com.example.aplikasigeneratorsoal.daftartulisan

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.aplikasigeneratorsoal.R
import com.example.aplikasigeneratorsoal.quiz.Bacaan
import com.example.aplikasigeneratorsoal.quiz.QuizJsonFileActivity
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException



class TextJsonFileActivity : AppCompatActivity() {
    private lateinit var teksBacaan: TextView
    private lateinit var kerjakanSoal: Button
    private lateinit var judulBacaan: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.read)

        val jsonFileName = intent.getStringExtra("json_file_name")
        val jsonFileContent = getJsonFileContent(jsonFileName.toString())

        // Display the JSON file content, e.g., in a TextView
        val teksBacaan = findViewById<TextView>(R.id.readTextInput2)
        val judulBacaan = findViewById<TextView>(R.id.buatTulisanTitle)
        val kerjakanSoal = findViewById<Button>(R.id.readButton1)
        teksBacaan.text = jsonFileContent
        judulBacaan.text = jsonFileName.toString()

        kerjakanSoal.setOnClickListener {
            val judulBacaan = judulBacaan.text.toString()
            val intent = Intent(this, QuizJsonFileActivity::class.java)
            intent.putExtra("judulBacaan", judulBacaan)
            startActivity(intent)
        }
    }

    private fun getJsonFileContent(fileName: String): String {
        // Specify the Documents directory
        val directory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val textDirectory = File(directory, "text")
        val file = File(textDirectory, "$fileName-teks.json")

        // Check if the file exists
        if (!file.exists()) {
            throw FileNotFoundException("File not found")
        }

        // Read the content of the file
        val inputStream = FileInputStream(file)
        val buffer = ByteArray(inputStream.available())
        inputStream.read(buffer)
        inputStream.close() // Don't forget to close the stream

        val jsonString = String(buffer, Charsets.UTF_8)
//        return String(buffer, Charsets.UTF_8)

        val gson = Gson()
        return try {
            val Bacaan = gson.fromJson(jsonString, Bacaan::class.java)
            Bacaan.text
        } catch (e: JsonSyntaxException) {
            "Invalid JSON format"
        }


    }

}
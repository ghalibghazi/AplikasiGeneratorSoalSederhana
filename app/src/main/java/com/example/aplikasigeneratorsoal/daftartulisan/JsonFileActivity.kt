package com.example.aplikasigeneratorsoal.daftartulisan

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.aplikasigeneratorsoal.R
import com.example.aplikasigeneratorsoal.editsoal.QuestionActivity2
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStreamReader


class JsonFileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.json_file)

        val jsonFileName = intent.getStringExtra("json_file_name")
        val jsonFileNameClean = jsonFileName.toString().replace("-teks.json", "")
        val jsonFileContent = getJsonFileContent2(jsonFileName.toString())
        // Display the JSON file content, e.g., in a TextView
        val tulisan = findViewById<TextView>(R.id.tulisanTextView)
        val judul = findViewById<TextView>(R.id.judulTulisan)
        val editSoal = findViewById<Button>(R.id.tulisanButton2)
        tulisan.text = jsonFileContent
        judul.text = jsonFileNameClean

        editSoal.setOnClickListener {
            loadQuestionJsonFile(jsonFileNameClean)
        }

    }

    private fun getJsonFileContent(fileName: String): String {
        val documentsDirectory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val fileDir = File(documentsDirectory, "text")
        val file = File(fileDir, fileName)
        if (!file.exists()) {
            throw FileNotFoundException("File not found: $fileName")
        }
        val inputStream = FileInputStream(file)
        val buffer = ByteArray(inputStream.available())
        inputStream.read(buffer)
        inputStream.close() // Don't forget to close the stream
        return String(buffer, Charsets.UTF_8)
    }

    private fun getJsonFileContent2(fileName: String): String {
        // Specify the Documents directory
        val documentsDirectory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val fileDir = File(documentsDirectory, "text")
        val file = File(fileDir, fileName)

        // Check if the file exists
        if (!file.exists()) {
            throw FileNotFoundException("File not found: $fileName")
        }

        // Read the content of the file
        val jsonString = FileInputStream(file).use { inputStream ->
            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)
            String(buffer, Charsets.UTF_8)
        }

        // Parse the JSON and remove formatting
        val jsonObject = JSONObject(jsonString)
        return jsonObject.getString("text")
    }

    private fun loadQuestionJsonFile(jsonFileNameClean: String) {
        val fileName = "$jsonFileNameClean-question.json"
        val documentsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val fileDir = File(documentsDirectory, "questions")
        val file = File(fileDir, fileName)

        if (file.exists()) {
            val inputStream = FileInputStream(file)
            val reader = InputStreamReader(inputStream)
            val jsonString = reader.readText()
            reader.close()

            val intent = Intent(this, QuestionActivity2::class.java)
            intent.putExtra("json_data", jsonString)
            intent.putExtra("judulText", jsonFileNameClean)
            startActivity(intent)
        } else {
            println("No data received")
        }
    }
}
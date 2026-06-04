package com.example.aplikasigeneratorsoal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import android.content.Context
import android.os.Environment
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.WindowCompat
import org.json.JSONObject
import java.io.File
import java.io.FileWriter
import java.io.IOException


class BuatTulisan : ComponentActivity() {
    private lateinit var buatSoal: Button
//    private lateinit var simpan: Button
    private lateinit var textBacaan: EditText
    private lateinit var judulBacaan: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, true)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.buat_tulisan)
        buatSoal = findViewById(R.id.buatTulisanButton2)
//        simpan = findViewById(R.id.buatTulisanButton1)
        textBacaan = findViewById(R.id.buatTulisanTextInput2)
        judulBacaan = findViewById(R.id.readTextInput1)

        val functions = arrayOf("Gemini", "ChatGPT")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, functions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)



        buatSoal.setOnClickListener {
            val judul = judulBacaan.text.toString()
            val inputText = textBacaan.text.toString()
            val intent = Intent(this, BuatSoal::class.java)
            intent.putExtra("inputText", inputText)
            intent.putExtra("judul", judul)
            saveTextAsJson(this, textBacaan, "$judul-teks")
            startActivity(intent)
        }


//        simpan.setOnClickListener {
//            val judul = judulBacaan.text.toString()
//            saveTextAsJson(this, textBacaan, "$judul-teks")
//        }


    }
    fun saveTextAsJson(context: Context, textBacaan: TextView, fileName: String) {
        try {
            // Get the text from the TextView
            val text = textBacaan.text.toString()

            // Create a new JSONObject
            val jsonObject = JSONObject()
            jsonObject.put("text", text)


            val documentsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
            val fileDir = File(documentsDirectory, "text")

//            val documentsDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
//            val textDir = File(documentsDir, "text")

            if (!fileDir.exists()) {
                fileDir.mkdirs()
            }
            // Create a new FileWriter
//            val fileWriter = FileWriter(context.getExternalFilesDir(null).toString() + "/" + fileName + ".json")
            val fileWriter = FileWriter(File(fileDir, "$fileName.json"))

            // Write the JSONObject to the file
            fileWriter.write(jsonObject.toString())
            fileWriter.close()

            Toast.makeText(context, "Text saved as JSON file", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Toast.makeText(context, "Error saving text as JSON file", Toast.LENGTH_SHORT).show()
        }
    }



    }


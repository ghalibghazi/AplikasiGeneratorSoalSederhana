package com.example.aplikasigeneratorsoal

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import android.text.Editable
import androidx.activity.ComponentActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.example.aplikasigeneratorsoal.editsoal.Question
import com.example.aplikasigeneratorsoal.editsoal.QuestionActivity
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class BuatSoal : ComponentActivity() {
    var chatTextView: TextView? = null
    var messageList: MutableList<String>? = null
    var client: OkHttpClient = OkHttpClient()

    private lateinit var judulText: TextView
    private lateinit var soal: TextView
    private lateinit var buatSoalBaru: Button
    private lateinit var editSoal: Button
    private lateinit var jsonText: String

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, true)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.buat_soal)
        judulText = findViewById(R.id.buatSoalTextInput1)
        soal = findViewById(R.id.buatTulisanTextInput2)
        editSoal = findViewById(R.id.buatSoalButton2)
        buatSoalBaru = findViewById(R.id.buatSoalButton1)

        messageList = ArrayList()

        val inputText = intent.getStringExtra("inputText")
        val judul = intent.getStringExtra("judul")
//        val selectedservice = intent.getStringExtra("selectedservice")
        judulText.text = judul ?: "No Input"
        if(inputText != null) {
            generateContent(inputText)
        }
//        if(inputText != null) {
//            when (selectedservice) {
//                "Gemini" -> generateContent(inputText)
//                "ChatGPT" -> callAPI(inputText)
//            }
//        }

        buatSoalBaru.setOnClickListener {
            if(inputText != null) {
                callAPI(inputText)
            }
        }
        editSoal.setOnClickListener{
            val judulText = judulText.text.toString()
            val intent = Intent(this, QuestionActivity::class.java)
//            intent.putExtra("question_text", question_text)
            intent.putExtra("question_text", jsonText)
            intent.putExtra("judulText", judulText)
            startActivity(intent)

        }
    }

    fun addToChat(message: String, sentBy: String) {
        runOnUiThread {
            val chatMessage = "$sentBy $message\n"
            chatTextView!!.append(chatMessage)
        }
    }

    fun addResponse(response: String) {
        runOnUiThread {
            val chatMessage = "Bot: $response\n"
            chatTextView!!.append(chatMessage)
        }
    }

     fun callAPI(inputText: String) {
         val client = OkHttpClient()

         // Safety check
         if (inputText.isNullOrBlank()) {
             println("Input text is empty.")
             return
         }

         val jsonBody = JSONObject().apply {
             put("model", "gpt-4o") // <-- Replace with the correct model ID for your use
             put("messages", JSONArray().apply {
                 put(JSONObject().apply {
                     put("role", "user")
                     put("content", "buat 3 soal pilihan ganda dan jawaban dari paragraf atau topik: $inputText")
                 })
             })
             put("max_tokens", 400)
             put("temperature", 0.7)
         }

         val mediaType = "application/json; charset=utf-8".toMediaType()
         val requestBody = jsonBody.toString().toRequestBody(mediaType)

         val request = Request.Builder()
             .url("https://api.openai.com/v1/chat/completions")
             .header("Authorization", "") // Replace with your actual API key
             .post(requestBody)
             .build()

         CoroutineScope(Dispatchers.IO).launch {
             try {
                 val response = client.newCall(request).execute()
                 if (response.isSuccessful) {
                     val responseString = response.body?.string()
                     val jsonResponse = JSONObject(responseString)
                     val result = jsonResponse
                         .getJSONArray("choices")
                         .getJSONObject(0)
                         .getJSONObject("message")
                         .getString("content")

                     println("Response:\n$result")

                     // Optionally: parseQuestions(result) if needed
                     // loadQuestions(parseQuestions(result))

                 } else {
                     println("API Error: ${response.code} - ${response.message}")
                     val errorBody = response.body?.string()
                     println("Error Body: $errorBody")
                 }
             } catch (e: Exception) {
                 e.printStackTrace()
                 println("Exception: ${e.message}")
             }
         }
     }

    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    companion object {
        val JSON: MediaType = "application/json; charset=utf-8".toMediaType()
    }

    data class PromptSetting(
        val jumlahSoal: Int,
        val jenisSoal: String,
        val bahasaSoal: String,
        val tipeHots: String
    )

    private fun loadSetting(): PromptSetting? {
        return try {
            val file = File(filesDir, "settings.json")
            if (!file.exists()) return null
            val json = file.readText()
            Gson().fromJson(json, PromptSetting::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

     // settext to editsoal
    private fun generateContent(inputText: String) {
         val setting = loadSetting()
         if (setting != null) {
             val prompt =
                 "Buat ${setting.jumlahSoal} soal ${setting.jenisSoal} berbahasa ${setting.bahasaSoal} objektif dengan dimensi kognitif ${setting.tipeHots} dari paragraf atau topik: $inputText"
             // Call the Gemini API to generate text
             lifecycleScope.launch {
                 val response = generativeModel.generateContent(prompt)
                 val questions = parseQuestions(response.text.toString())
                 loadQuestions(questions)
//            soalJson.text = response.text.toString()
                 jsonText = response.text.toString()
             }
         } else {
             println("Generate Error")
         }
     }

     //untuk fungsi buat soal baru (gemini)
    private fun generateNewContent(inputText: String) {
        val prompt = "buat 2 soal pilihan ganda baru dari paragraf: $inputText"

        lifecycleScope.launch {
            val response = generativeModel.generateContent(prompt)
            val questions = parseQuestions(response.toString())
            loadQuestions(questions)
        }
    }

    private fun parseQuestions(jsonString: String): List<Question> {
        val questionsList = mutableListOf<Question>()
        try {
            // Assuming the result is a JSON array of question objects
            val jsonArray = JSONArray(jsonString)
            for (i in 0 until jsonArray.length()) {
                val questionObject = jsonArray.getJSONObject(i)
                val pertanyaan = questionObject.getString("pertanyaan")
                val pilihan = questionObject.getJSONArray("pilihan")
                val jawaban = questionObject.getString("jawaban")

                // Convert pilihan JSONArray to List<String>
                val pilihanList = mutableListOf<String>()
                for (j in 0 until pilihan.length()) {
                    pilihanList.add(pilihan.getString(j))
                }

                // Create a Question object and add it to the list
                questionsList.add(Question(pertanyaan, pilihanList, jawaban))
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return questionsList
    }

     private fun loadQuestions(questions: List<Question>) {
         val stringBuilder = StringBuilder()
         for (question in questions) {
             stringBuilder.append("${question.pertanyaan}\n\n") // Question with a newline

             for (option in question.pilihan) {
                 stringBuilder.append("\t- $option\n") // Indented options with a dash
             }

             stringBuilder.append("\nJawaban: ${question.jawaban}\n\n") // Answer with a newline
         }
         soal.text = stringBuilder.toString()
     }
}
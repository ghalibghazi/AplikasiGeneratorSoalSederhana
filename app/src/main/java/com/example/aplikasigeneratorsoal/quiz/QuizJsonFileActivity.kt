package com.example.aplikasigeneratorsoal.quiz

import android.os.Bundle
import android.os.Environment
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasigeneratorsoal.R
import com.example.aplikasigeneratorsoal.editsoal.Question
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStreamReader


class QuizJsonFileActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var quizAdapter: QuizAdapter
    private lateinit var questions: List<Question>
    private lateinit var viewModel: QuizViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz)

        val jsonFileName = intent.getStringExtra("judulBacaan")
        viewModel = ViewModelProvider(this).get(QuizViewModel::class.java)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        questions = loadQuestionsFromJson(jsonFileName.toString())
        quizAdapter = QuizAdapter(questions) { position, selectedOption ->
            viewModel.selectedOptions.value?.put(position, selectedOption)
        }
        recyclerView.adapter = quizAdapter

        viewModel.selectedOptions.observe(this) { selectedOptions ->
            quizAdapter.selectedOptions.clear()
            quizAdapter.selectedOptions.putAll(selectedOptions)
        }

        findViewById<Button>(R.id.submitButton).setOnClickListener {
            val score = calculateScore()
            viewModel.score.value = score
            showScore(score)
        }
    }

    private fun calculateScore(): Int {
        var score = 0
        questions.forEachIndexed { index, question ->
            // Compare the selected option (String) with the correct answer (String)
            if (viewModel.selectedOptions.value?.get(index) == question.jawaban) {
                score++
            }
        }
        return score
    }

//    private fun showScore(score: Int) {
//        Toast.makeText(this, "Your score: $score/${questions.size}", Toast.LENGTH_LONG).show()
//    }

    private fun showScore(score: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Quiz Completed")
        builder.setMessage("Your score is $score out of ${questions.size}")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
            finish() // Close the app or restart the quiz
        }
        builder.show()
    }

    private fun loadQuestionsFromJson(fileName: String): List<Question> {
        val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val textDirectory = File(directory, "questions")
        val file = File(textDirectory, "$fileName-question.json")

        // Check if the file exists
        if (!file.exists()) {
            throw FileNotFoundException("File not found: $fileName")
        }

        // Read the content of the file
        val inputStream = FileInputStream(file)
        val reader = InputStreamReader(inputStream)
        val type = object : TypeToken<List<Question>>() {}.type
        val questions: List<Question> = Gson().fromJson(reader, type)

        inputStream.close()
        return questions
    }
}
package com.example.aplikasigeneratorsoal.editsoal
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import com.google.gson.Gson
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasigeneratorsoal.BuatTulisan
import com.example.aplikasigeneratorsoal.Home
import com.example.aplikasigeneratorsoal.R
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph

class QuestionActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var simpanJson: Button
    private lateinit var simpanPdf: Button
    private lateinit var judulText2: EditText
    private lateinit var questions: List<Question>
    private lateinit var kembaliHome: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, true)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_soal)

        recyclerView = findViewById(R.id.recycler_view)
        simpanJson = findViewById(R.id.editSoalButton1)
        simpanPdf = findViewById(R.id.editSoalButton2)
        judulText2 = findViewById(R.id.editSoalTextInput1)
        kembaliHome = findViewById(R.id.editSoalButton3)

        val judulText = intent.getStringExtra("judulText")
        judulText2.setText(judulText ?: "No Input")
        val questionText = intent.getStringExtra("question_text")
        if (questionText != null) {
            // Process the question text here
            questions = parseQuestionText(questionText)
            setupRecyclerView(questions)
        } else {
            println("error")
            // Handle the case when questionText is null
        }

        simpanJson.setOnClickListener {
            saveQuestionsToJson(questions)
        }

        simpanPdf.setOnClickListener {
            saveQuestionsToPdf3(questions)
            showPopupDialog()

        }

        kembaliHome.setOnClickListener {
            startActivity(Intent(this, Home::class.java))

        }
    }

    private fun parseQuestionText(questionText: String): List<Question> {
        val gson = Gson()
        // Define a TypeToken for the list of Question objects
        val type = object : TypeToken<List<Question>>() {}.type
        // Parse the JSON string into a list of Question objects
        val questions: List<Question> = gson.fromJson(questionText, type)
        return questions
    }

    private fun setupRecyclerView(questions: List<Question>) {
        recyclerView?.apply {
            layoutManager = LinearLayoutManager(this@QuestionActivity)
            adapter = QuestionAdapter(questions)
        }
    }

    private fun getUpdatedQuestions(): List<Question> = questions

    private fun saveQuestionsToJson(questions: List<Question>) {
        val gson = Gson()
        val jsonString = gson.toJson(questions)
        val judulFile = judulText2.text.toString()

        // Specify the file name and path
        val fileName = "$judulFile-question.json"
        val documentsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val fileDir = File(documentsDirectory, "questions")

        if (!fileDir.exists()) {
            fileDir.mkdirs()
        }

        try {
            val fileWriter = FileWriter(File(fileDir, "$fileName"))
            fileWriter.write(jsonString.toString())
            fileWriter.close()

            Toast.makeText(this, "Questions saved to $fileName", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error saving questions", Toast.LENGTH_SHORT).show()
        }
    }

    fun saveQuestionsToPdf3(questions: List<Question>) {
        val gson = Gson()
        val jsonString = gson.toJson(questions)
        val judulFile = judulText2.text.toString()
        val fileName = "$judulFile-question.pdf"

        val documentsDirectory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val questionsFileDir = File(documentsDirectory, "questions")
        val answerFileDir = File(questionsFileDir, "answer")

        if (!questionsFileDir.exists()) {
            questionsFileDir.mkdirs()
        }

        if (!answerFileDir.exists()) {
            answerFileDir.mkdirs()
        }

        try {
            val questionsPdfWriter = PdfWriter(File(questionsFileDir, fileName))
            val questionsPdfDocument = com.itextpdf.kernel.pdf.PdfDocument(questionsPdfWriter)
            val questionsDocument = Document(questionsPdfDocument)

            questionsDocument.add(Paragraph("Nama: ").setBold().setFontSize(16f))
            questionsDocument.add(Paragraph("Kelas: ").setBold().setFontSize(16f))
            questionsDocument.add(Paragraph("").setBold().setFontSize(16f))

            // Convert JSON back to list of Question objects
            val questionListType = object : TypeToken<List<Question>>() {}.type
            val questionsList: List<Question> = gson.fromJson(jsonString, questionListType)

            // Format and append each question
            questionsList.forEachIndexed { index, question ->
                questionsDocument.add(Paragraph("${index + 1}. ${question.pertanyaan}").setBold().setFontSize(14f))
//                document.add(Paragraph("Answer: ${question.jawaban}").setItalic())
                questionsDocument.add(Paragraph("").setBold().setFontSize(14f))
//                document.add(Paragraph("Choices:"))
                val choiceLabels = listOf("A.", "B.", "C.", "D.") // Choice labels
                question.pilihan.forEachIndexed { choiceIndex, choice ->
                    val label = if (choiceIndex < choiceLabels.size) choiceLabels[choiceIndex] else "${choiceIndex + 1}."
                    questionsDocument.add(Paragraph("$label $choice"))
                }
                questionsDocument.add(Paragraph("\n")) // Add spacing
            }

            questionsDocument.close()
            println("PDF saved successfully at: ${questionsFileDir.absolutePath}")
            Toast.makeText(this, "PDF saved successfully at: ${questionsFileDir.absolutePath}", Toast.LENGTH_SHORT).show()

            val answerPdfWriter = PdfWriter(File(answerFileDir, fileName))
            val answerPdfDocument = com.itextpdf.kernel.pdf.PdfDocument(answerPdfWriter)
            val answerDocument = Document(answerPdfDocument)

            answerDocument.add(Paragraph("Quiz Answers: $judulFile").setBold().setFontSize(16f))

            questionsList.forEachIndexed { index, question ->
                answerDocument.add(Paragraph("${index + 1}. ${question.pertanyaan}").setFontSize(14f))
                answerDocument.add(Paragraph("Answer: ${question.jawaban}").setItalic())
                answerDocument.add(Paragraph("\n")) // Add spacing
            }

            answerDocument.close()
            println("PDF saved successfully at: ${answerFileDir.absolutePath}")
            Toast.makeText(this, "PDF saved successfully at: ${answerFileDir.absolutePath}", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            println("Error saving PDF: ${e.message}")
        }
    }

    private fun showPopupDialog() {
        // Inflate the custom layout
        val dialogView = LayoutInflater.from(this).inflate(R.layout.popup_simpan, null)
        // Create and show the dialog
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true) // Allows closing by tapping outside
            .create()
        // Get references to views in the custom layout
        val actionButton = dialogView.findViewById<Button>(R.id.dialog_action_button)
        val homeButton = dialogView.findViewById<Button>(R.id.home_action_button)

        actionButton.setOnClickListener {
            openFileExplorer()
            dialog.dismiss()
        }
        homeButton.setOnClickListener {
            startActivity(Intent(this, Home::class.java))
        }
        dialog.show() // Show the dialog
    }

    private val folderPickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data ?: return@registerForActivityResult

            contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            )

            // Try to find or create folder "a" inside the selected folder (Documents)
            val folderAUri = findOrSendErrorFolder(uri, "questions")
            if (folderAUri != null) {
                Toast.makeText(this, "Accessed folder 'questions': $folderAUri", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Failed to access folder 'questions'", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openFileExplorer() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        }
        folderPickerLauncher.launch(intent)
    }

    private fun findOrSendErrorFolder(parentUri: Uri, folderName: String): Uri? {
        val childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(
            parentUri,
            DocumentsContract.getTreeDocumentId(parentUri)
        )

        val cursor = contentResolver.query(
            childrenUri,
            arrayOf(
                DocumentsContract.Document.COLUMN_DOCUMENT_ID,
                DocumentsContract.Document.COLUMN_DISPLAY_NAME,
                DocumentsContract.Document.COLUMN_MIME_TYPE
            ),
            null,
            null,
            null
        )

        cursor?.use {
            while (it.moveToNext()) {
                val name = it.getString(1)
                val mime = it.getString(2)
                if (name == folderName && mime == DocumentsContract.Document.MIME_TYPE_DIR) {
                    val docId = it.getString(0)
                    return DocumentsContract.buildDocumentUriUsingTree(parentUri, docId)
                }
            }
        }

        // Folder not found, log and return null
        Log.e("FolderAccess", "Folder '$folderName' not found in selected directory.")
        Toast.makeText(this, "Folder '$folderName' not found.", Toast.LENGTH_SHORT).show()
        return null
    }
}
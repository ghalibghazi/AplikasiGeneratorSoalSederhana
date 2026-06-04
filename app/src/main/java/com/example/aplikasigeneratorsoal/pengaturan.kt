package com.example.aplikasigeneratorsoal

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.File

class pengaturan : AppCompatActivity() {

    private lateinit var jumlahSoalInput: EditText
    private lateinit var jenisSoalInput: Spinner
    private lateinit var bahasaSoalInput: Spinner
    private lateinit var tipeHotsInput: Spinner
    private lateinit var simpanButton: Button
    private lateinit var exportSettingButton: Button
    private lateinit var importSettingButton: Button

    private val jenisSoalOptions = listOf("Pilihan Ganda")
    private val bahasaSoalOptions = listOf("Indonesia", "Inggris")
    private val tipeHotsOptions = listOf("C4", "C5", "C6")

    private lateinit var exportSettingFileLauncher: ActivityResultLauncher<Intent>
    private lateinit var importSettingFileLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting)

        jumlahSoalInput = findViewById(R.id.jumlahSoalInput2)
        jenisSoalInput = findViewById(R.id.jenisSoalInput2)
        bahasaSoalInput = findViewById(R.id.bahasaSoalInput2)
        tipeHotsInput = findViewById(R.id.tipeHotsInput2)
        simpanButton = findViewById(R.id.simpanButton)
        exportSettingButton = findViewById(R.id.exportButton)
        importSettingButton = findViewById(R.id.importButton)

        jenisSoalInput.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, jenisSoalOptions)
        bahasaSoalInput.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, bahasaSoalOptions)
        tipeHotsInput.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tipeHotsOptions)

        loadSettings()

        simpanButton.setOnClickListener {
            saveSettings()
        }
        exportSettingButton.setOnClickListener {
            launchExportSettingFileIntent()
        }
        importSettingButton.setOnClickListener {
            launchImportSettingFileIntent()
        }

        exportSettingFileLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.data?.let { uri ->
                    exportSettings(uri)
                }
            }
        }

        importSettingFileLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.data?.let { uri ->
                    importSettings(uri)
                }
            }
        }
    }

    private fun saveSettings() {
        try {
            val json = JSONObject().apply {
                put("jumlahSoal", jumlahSoalInput.text.toString())
                put("jenisSoal", jenisSoalInput.selectedItem.toString())
                put("bahasaSoal", bahasaSoalInput.selectedItem.toString())
                put("tipeHots", tipeHotsInput.selectedItem.toString())

            }

            val file = File(filesDir, "settings.json")
            file.writeText(json.toString())

            Toast.makeText(this, "Settings saved", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Save failed: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun loadSettings() {
        try {
            val file = File(filesDir, "settings.json")
            if (!file.exists()) {
                Toast.makeText(this, "No saved settings found", Toast.LENGTH_SHORT).show()
                return
            }

            val json = JSONObject(file.readText())

            jumlahSoalInput.setText(json.optString("jumlahSoal", ""))

            val spinner1Value = json.optString("jenisSoal", "")
            jenisSoalInput.setSelection(jenisSoalOptions.indexOf(spinner1Value).coerceAtLeast(0))

            val spinner2Value = json.optString("bahasaSoal", "")
            bahasaSoalInput.setSelection(bahasaSoalOptions.indexOf(spinner2Value).coerceAtLeast(0))

            val spinner3Value = json.optString("tipeHots", "")
            tipeHotsInput.setSelection(tipeHotsOptions.indexOf(spinner3Value).coerceAtLeast(0))

            Toast.makeText(this, "Settings loaded", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Load failed: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun launchExportSettingFileIntent() {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/json"
            putExtra(Intent.EXTRA_TITLE, "settings.json")
        }
        exportSettingFileLauncher.launch(intent)
    }

    private fun launchImportSettingFileIntent() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/json"
        }
        importSettingFileLauncher.launch(intent)
    }

    private fun exportSettings(uri: Uri) {
        try {
            val file = File(filesDir, "settings.json")
            if (!file.exists()) {
                Toast.makeText(this, "No settings to export", Toast.LENGTH_SHORT).show()
                return
            }

            val jsonText = file.readText()

            contentResolver.openOutputStream(uri)?.use { output ->
                output.write(jsonText.toByteArray())
                output.flush()
            }

            Toast.makeText(this, "Export successful", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Export failed: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun importSettings(uri: Uri) {
        try {
            val inputStream = contentResolver.openInputStream(uri)
            val jsonText = inputStream?.bufferedReader()?.readText()

            if (jsonText.isNullOrEmpty()) {
                Toast.makeText(this, "Selected file is empty", Toast.LENGTH_SHORT).show()
                return
            }

            // Save to internal storage
            val file = File(filesDir, "settings.json")
            file.writeText(jsonText)

            // Load immediately
            loadSettings()

            Toast.makeText(this, "Import successful", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Import failed: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

}
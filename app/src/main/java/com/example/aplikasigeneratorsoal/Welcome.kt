package com.example.aplikasigeneratorsoal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import java.io.File
import java.io.FileOutputStream

class Welcome : ComponentActivity() {
    private lateinit var login: Button
    private lateinit var register: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.welcome)
        login = findViewById(R.id.welcomeLoginButton)
        register = findViewById(R.id.welcomeRegisterButton)

        copyDefaultSettingsIfNeeded(this)

        login.setOnClickListener {

            startActivity(Intent(this, Login::class.java))
        }

    }

    private fun copyDefaultSettingsIfNeeded(context: Context) {
        val fileName = "settings.json"
        val targetFile = File(context.filesDir, fileName)

        if (!targetFile.exists()) {
            val assetManager = context.assets
            assetManager.open("default_setting.json").use { inputStream ->
                FileOutputStream(targetFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        }
    }

}
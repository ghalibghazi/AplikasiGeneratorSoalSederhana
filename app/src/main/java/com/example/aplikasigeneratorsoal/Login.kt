package com.example.aplikasigeneratorsoal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat

class
Login : ComponentActivity() {
    private lateinit var login: Button
    private lateinit var idInput: EditText
    private lateinit var passwordInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login)
        login = findViewById(R.id.loginLoginButton)
        idInput = findViewById(R.id.idInput)
        passwordInput = findViewById(R.id.passwordInput)


//        login.setOnClickListener {
//            startActivity(Intent(this, Home::class.java))
//        }

        login.setOnClickListener {
            val id = idInput.text.toString()
            val pass = passwordInput.text.toString()

            when {
                id == "admin" && pass == "1234" -> {
                    // Start AdminActivity
                    startActivity(Intent(this, Home::class.java))
                }

                id == "murid" && pass == "murid" -> {
                    // Start UserActivity
                    startActivity(Intent(this, SiswaHome::class.java))
                }

                else -> {
                    // Start DefaultActivity or show an error
                    println("error")
                }
            }
        }
    }
}
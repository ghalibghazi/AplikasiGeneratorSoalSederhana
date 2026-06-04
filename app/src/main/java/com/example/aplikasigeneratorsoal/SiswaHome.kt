package com.example.aplikasigeneratorsoal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.aplikasigeneratorsoal.quiz.TextJsonActivity

class SiswaHome : ComponentActivity() {
    private lateinit var kerjakanSoal: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.siswa_home)
        kerjakanSoal = findViewById(R.id.siswaHomeButton1)


        kerjakanSoal.setOnClickListener {
            startActivity(Intent(this, TextJsonActivity::class.java))

        }



    }
}
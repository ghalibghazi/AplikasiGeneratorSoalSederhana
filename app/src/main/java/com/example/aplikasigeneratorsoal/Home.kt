package com.example.aplikasigeneratorsoal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import com.example.aplikasigeneratorsoal.daftarsiswa.SiswaActivity
import com.example.aplikasigeneratorsoal.daftartulisan.JsonActivity

class Home : ComponentActivity() {
    private lateinit var buatTulisanBaru: Button
    private lateinit var daftarTulisan: Button
    private lateinit var pengaturanSoal: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.home)
        buatTulisanBaru = findViewById(R.id.homeButton1)
        daftarTulisan = findViewById(R.id.homeButton2)
        pengaturanSoal = findViewById(R.id.homeButton3)

        buatTulisanBaru.setOnClickListener {
            startActivity(Intent(this, BuatTulisan::class.java))

        }

        daftarTulisan.setOnClickListener {
            startActivity(Intent(this, JsonActivity::class.java))
        }

        pengaturanSoal.setOnClickListener {
            startActivity(Intent(this, pengaturan::class.java))
        }

    }
}
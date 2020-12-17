package com.example.psiprojek

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_daftar_pasien.*

class DaftarPasienActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_pasien)

        btnTambahPasien.setOnClickListener {
            val intent = Intent(this, TambahPasienActivity::class.java)
            startActivity(intent)
        }
    }
}
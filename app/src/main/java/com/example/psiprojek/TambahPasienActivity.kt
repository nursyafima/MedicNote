package com.example.psiprojek

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_tambah_pasien.*

class TambahPasienActivity : AppCompatActivity() {

    lateinit var ref :DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_pasien)

        ref = FirebaseDatabase.getInstance().getReference("Pasien")

        btnTambahPasienNow.setOnClickListener {
            saveData()
        }

    }

    private fun saveData(){
        val nama = editAddNamaPasien.text.toString()
        val keluhan = editAddKeluhanPasien.text.toString()
        val penyakit = editAddPenyakitSebelum.text.toString()
        val tinggiBeratBadan = editAddTinggiBeratBadan.text.toString()
        val tensi = editAddTensi.text.toString()
        val hasil = editAddHasilDiagnosa.text.toString()
        val resepObat = editAddResepObat.text.toString()

        val pasien = Pasien(nama,keluhan, penyakit, tinggiBeratBadan, tensi, hasil, resepObat)
        val pasienId = ref.push().key.toString()

        ref.child(pasienId).setValue(pasien).addOnCompleteListener {
            Toast.makeText(this, "Successs",Toast.LENGTH_SHORT).show()
            editAddNamaPasien.setText("")
            editAddKeluhanPasien.setText("")
            editAddPenyakitSebelum.setText("")
            editAddTinggiBeratBadan.setText("")
            editAddTensi.setText("")
            editAddHasilDiagnosa.setText("")
            editAddResepObat.setText("")
        }
    }
}
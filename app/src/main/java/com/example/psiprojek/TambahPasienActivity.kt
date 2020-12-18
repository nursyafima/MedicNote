package com.example.psiprojek

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import com.example.psiprojek.Cons.PATH_COLLECTION
import com.example.psiprojek.Cons.setTimeStamp
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_tambah_pasien.*

class TambahPasienActivity : AppCompatActivity() {

    companion object {
        //key untuk intent data
        const val EXTRA_DATA = "extra_data"
        const val REQ_EDIT = "req_edit"
    }

    private var isEdit = false
    private var pasiens: Pasien? = null
    private val mFirestore = FirebaseFirestore.getInstance()
    private val mPasienCollection = mFirestore.collection(PATH_COLLECTION)

    lateinit var ref :DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_pasien)
        //ambil data yang dibawa dari main activity sesuai keynya masing-masing
        isEdit = intent.getBooleanExtra(REQ_EDIT, false)
        pasiens = intent.getParcelableExtra(EXTRA_DATA)

        btnTambahPasienNow.setOnClickListener{ saveData() }
        initView()

//        ref = FirebaseDatabase.getInstance().getReference("Pasien")
//
//        btnTambahPasienNow.setOnClickListener {
//            saveData()
//        }

    }

//    private fun saveData(){
//        val nama = editAddNamaPasien.text.toString()
//        val keluhan = editAddKeluhanPasien.text.toString()
//        val penyakit = editAddPenyakitSebelum.text.toString()
//        val tinggiBeratBadan = editAddTinggiBeratBadan.text.toString()
//        val tensi = editAddTensi.text.toString()
//        val hasil = editAddHasilDiagnosa.text.toString()
//        val resepObat = editAddResepObat.text.toString()
//
//        val pasienId = ref.push().key.toString()
//        val pasien = Pasien(pasienId, nama,keluhan, penyakit, tinggiBeratBadan, tensi, hasil, resepObat)
//
//        ref.child(pasienId).setValue(pasien).addOnCompleteListener {
//            Toast.makeText(this, "Successs",Toast.LENGTH_SHORT).show()
//            editAddNamaPasien.setText("")
//            editAddKeluhanPasien.setText("")
//            editAddPenyakitSebelum.setText("")
//            editAddTinggiBeratBadan.setText("")
//            editAddTensi.setText("")
//            editAddHasilDiagnosa.setText("")
//            editAddResepObat.setText("")
//        }
//    }


    private fun initView() {
        //set view jika data di edit maka akan tampil pada form input
        if (isEdit) {
            btnTambahPasienNow.text = "Update"
            editAddNamaPasien.text = Editable.Factory.getInstance().newEditable(pasiens?.nama)
            editAddHasilDiagnosa.text = Editable.Factory.getInstance().newEditable(pasiens?.hasil)
            editAddKeluhanPasien.text = Editable.Factory.getInstance().newEditable(pasiens?.keluhan)
            editAddPenyakitSebelum.text = Editable.Factory.getInstance().newEditable(pasiens?.penyakit)
            editAddTinggiBeratBadan.text = Editable.Factory.getInstance().newEditable(pasiens?.tinggiBeratBadan)
            editAddTensi.text = Editable.Factory.getInstance().newEditable(pasiens?.tensi)
            editAddResepObat.text = Editable.Factory.getInstance().newEditable(pasiens?.resepObat)
        }
    }

    private fun saveData() {
        setData(pasiens?.id)
    }

    private fun setData(id: String?) {
        createPengunjung(id).addOnCompleteListener  {
            val TAG = "InputData"
            if (it.isSuccessful) {
                if (isEdit) {
                    Toast.makeText(this, "Sukses edit data", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d(TAG,"Transaction success")
                    Toast.makeText(this, "Sukses tambah data", Toast.LENGTH_SHORT).show()
                }
                finish()
            } else {
                Toast.makeText(this, "Gagal tambah data", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Error Added data ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }

    //fungsi untuk mengambil input data dan simpan pada firestore
    private fun createPengunjung(strId: String?): Task<Void> {
        val writeBatch = mFirestore.batch()
        val path = PATH_COLLECTION + setTimeStamp().toString() //exmp hasil : pengunjung-43287845
        val id = strId ?: path
        val nama = editAddNamaPasien.text.toString()
        val keluhan = editAddKeluhanPasien.text.toString()
        val penyakit = editAddPenyakitSebelum.text.toString()
        val tinggiBeratBadan = editAddTinggiBeratBadan.text.toString()
        val tensi = editAddTensi.text.toString()
        val hasil = editAddHasilDiagnosa.text.toString()
        val resepObat = editAddResepObat.text.toString()

        val pengunjungs = Pasien(id, nama, keluhan, penyakit, tinggiBeratBadan, tensi, hasil, resepObat)
        writeBatch.set(mPasienCollection.document(id), pengunjungs) //simpan data dengan id yang ditentukan
        return writeBatch.commit()
    }
}
package com.example.psiprojek

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.CollectionReference
import kotlinx.android.synthetic.main.rv_pasien.view.*

class Adapter(
    private val context: Context,
    private val collection: CollectionReference,
    options: FirestoreRecyclerOptions<Pasien>
)   : FirestoreRecyclerAdapter<Pasien, Adapter.PasienViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.PasienViewHolder {
        return PasienViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_pasien, parent, false))
    }

    override fun onBindViewHolder(p0: Adapter.PasienViewHolder, p1: Int, p2: Pasien) {
        p0.bindItem(p2)
        p0.itemView.setOnClickListener {
            showDialogMenu(p2)
        }
    }

    class PasienViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(rvPasien: Pasien) {
            view.apply {
                val nama = "Nama   : ${rvPasien.nama}"
                val hasil = "Hasil   : ${rvPasien.hasil}"
                val keluhan = "Keluhan   : ${rvPasien.keluhan}"
                val penyakit = "Penyakit Sebelumnya   : ${rvPasien.penyakit}"
                val tinggiBeratBadan = "Tinggi Berat Badan  : ${rvPasien.tinggiBeratBadan}"
                val tensi = "Tensi : ${rvPasien.tensi}"
                val resep = "Resep Obat : ${rvPasien.resepObat}"

                rvNama.text = nama
                rvHasil.text = hasil
                rvKeluhan.text = keluhan
                rvPenyakit.text = penyakit
                rvTinggiBeratBadan.text = tinggiBeratBadan
                rvTensi.text = tensi
                rvResepObat.text = resep
            }
        }

    }

    private fun showDialogMenu(rvPasien: Pasien) {
        // dialog popup menu edit dan hapus
        val builder = androidx.appcompat.app.AlertDialog.Builder(context, R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
        val option = arrayOf("Edit", "Hapus")
        builder.setItems(option) { dialog, which ->
            when (which) {
                //0 -> untuk berpindah ke activity AddEditActivity untuk edit dengan membawa data
                0 -> context.startActivity(Intent(context, TambahPasienActivity::class.java).apply {
                    putExtra(TambahPasienActivity.REQ_EDIT, true)
                    putExtra(TambahPasienActivity.EXTRA_DATA, rvPasien)
                })
                1 -> showDialogDel(rvPasien.id)
            }
        }
        builder.create().show()
    }

    private fun showDialogDel(strId: String) {
        //dialog pop delete
        val builder = androidx.appcompat.app.AlertDialog.Builder(context)
            .setTitle("Hapus Data")
            .setMessage("Yakin nih mau hapus?")
            .setPositiveButton(android.R.string.yes) { dialog, which ->
                deleteById(strId)
            }
            .setNegativeButton(android.R.string.cancel, null)
        builder.create().show()
    }

    private fun deleteById(id: String) {
        //menghapus data berdasarkan id
        collection.document(id)
            .delete()
            .addOnCompleteListener { Toast.makeText(context, "Success Hapus data", Toast.LENGTH_SHORT).show() }
            .addOnFailureListener { Toast.makeText(context, "Gagal Hapus data", Toast.LENGTH_SHORT).show() }
    }

}
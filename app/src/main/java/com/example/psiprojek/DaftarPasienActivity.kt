package com.example.psiprojek

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.psiprojek.Cons.PATH_COLLECTION
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_daftar_pasien.*

class DaftarPasienActivity : AppCompatActivity() {

//    lateinit var ref : DatabaseReference
//    lateinit var list : MutableList<Pasien>
//    lateinit var listView: ListView



    private lateinit var mAdapter: FirestoreRecyclerAdapter<Pasien, Adapter.PasienViewHolder>
    private val mFirestore = FirebaseFirestore.getInstance()
    private val mPasienCollection = mFirestore.collection(PATH_COLLECTION)
    private val mQuery = mPasienCollection.orderBy("id", Query.Direction.ASCENDING)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_pasien)

        initView()
        setupAdapter()

//        ref = FirebaseDatabase.getInstance().getReference("Pasien")
//        list = mutableListOf()
//        listView = findViewById(R.id.listView)
//
//        ref.addValueEventListener(object : ValueEventListener {
//            override fun onCancelled(p0: DatabaseError) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//                if (p0!!.exists()){
//
//                    list.clear()
//                    for (h in p0.children){
//                        val pasien = h.getValue(Pasien::class.java)
//                        list.add(pasien!!)
//                    }
//                    val adapter = Adapter(this@DaftarPasienActivity,R.layout.rv_pasien,list)
//                    listView.adapter = adapter
//                }
//            }
//        })
//
    }

    private fun initView() {
//        prefHelper = PrefHelper(this)

//        val textUsername = findViewById<TextView>(R.id.textUsername)
//        textUsername.text = prefHelper.getString( Constant.PREF_USERNAME )

        listPasien.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@DaftarPasienActivity)
        }
        fab_addData.setOnClickListener {
            //berpindah ke activity AddEditActivity untuk tambah data
            startActivity(Intent(this, TambahPasienActivity::class.java).apply {
                putExtra(TambahPasienActivity.REQ_EDIT, false)
            })
        }
    }

    private fun setupAdapter() {
        //set adapter yang akan menampilkan data pada recyclerview
        val options = FirestoreRecyclerOptions.Builder<Pasien>()
            .setQuery(mQuery, Pasien::class.java)
            .build()
        mAdapter = Adapter(this, mPasienCollection, options)
        mAdapter.notifyDataSetChanged()
        listPasien.adapter = mAdapter
    }

    override fun onStart() {
        super.onStart()
        mAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        mAdapter.stopListening()
    }
}
package com.example.psiprojek

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth

        btnRegisterNow.setOnClickListener {
            val emailRegis = editEmail.text.toString()
            val passwordRegis = editPassword.text.toString()

            if(emailRegis.isEmpty()||passwordRegis.isEmpty()){
                Toast.makeText(this, "Ada Field Kosong", Toast.LENGTH_SHORT).show()
            }
            else if (passwordRegis.length < 6){
                Toast.makeText(this, "Password terlalu pendek", Toast.LENGTH_SHORT).show()
            }
            else{
                registerUser(emailRegis, passwordRegis)
            }
        }
    }

    fun registerUser(email: String, password:String){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this){task ->
            if (task.isSuccessful){
//                val user = auth.currentUser
                Toast.makeText(this, "Register Sukses", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else {
                Toast.makeText(this, "Register Gagal", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
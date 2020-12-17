package com.example.psiprojek

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        btnLoginNow.setOnClickListener {
            val emailLogin = editEmailLogin.text.toString()
            val passwordLogin = editPasswordLogin.text.toString()
            loginUser(emailLogin, passwordLogin)
        }
    }

    fun loginUser(email: String, password:String){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful){
//                val user = auth.currentUser
                Toast.makeText(this, "Login Sukses", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, DaftarPasienActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this, "Login Gagal", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
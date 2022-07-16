package com.example.birdiechat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()

        buSignupLoginPage.setOnClickListener {
            val intent = Intent(this , SignUp::class.java)
            startActivity(intent)
        }

        buLogin.setOnClickListener {
            val email = etEmailLogin.text.toString()
            val password = etPasswordLogin.text.toString()

            loginFunc(email , password)
        }
    }

    private fun loginFunc(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {task ->
                if (task.isSuccessful){
                    val intent = Intent(this , MainActivity::class.java)
                    finish()
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this, "User does not exist" , Toast.LENGTH_SHORT).show()
                }
            }
    }
}
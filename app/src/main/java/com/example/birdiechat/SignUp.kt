package com.example.birdiechat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.arpit.birdiechat.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth
     private lateinit var mDBRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()

        buSignupSignPage.setOnClickListener {
            val name = etNameSignup.text.toString()
            val email = etEmailSignup.text.toString()
            val password = etPasswordSignup.text.toString()
            signUpFunc(name, email, password)
        }
    }

    private fun signUpFunc(name : String ,email: String, password: String) {

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {task ->
                if (task.isSuccessful){
                      addUserToDatabase(name,email,mAuth.currentUser!!.uid)
                    Toast.makeText(this , "Sign up Successful" , Toast.LENGTH_SHORT).show()
                    val intent = Intent(this , MainActivity::class.java )
                   finish()
                    startActivity(intent)
                }else{
                    Log.e("SignUp", "id=$email , name=$name , password=$password")
                    Toast.makeText(this , "Sign up Failed" , Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String?) {
        mDBRef = FirebaseDatabase.getInstance().getReference()
        mDBRef.child("user").child(uid!!).setValue(User(name, email, uid))
    }
}
package com.shrutii.melodrama.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shrutii.melodrama.R

class LogInScreen : AppCompatActivity() {
    lateinit var emailEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var logInButton:Button
    lateinit var signInText:TextView
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in_screen)
        initViews()
        click()
        // Initialize Firebase Auth
        auth = Firebase.auth
    }
    private fun initViews()
    {
        emailEditText= findViewById(R.id.emailid)
        passwordEditText=findViewById(R.id.password)
        logInButton=findViewById(R.id.button)
        signInText=findViewById(R.id.signup)

    }
    private fun click()
    {
        signInText.setOnClickListener(){
            startActivity(Intent(this,SignUp::class.java))
        }
        logInButton.setOnClickListener(){

        }
    }
}
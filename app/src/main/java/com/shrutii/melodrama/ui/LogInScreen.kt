package com.shrutii.melodrama.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.shrutii.melodrama.R
import com.shrutii.melodrama.models.UserModel
import com.shrutii.melodrama.utils.AppSharedPref
import com.shrutii.melodrama.utils.StringUtils

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
            val isValid= checkField()
            isValid?.let {
                Toast.makeText(this,isValid,Toast.LENGTH_SHORT).show()
            }
                ?:run(){
                    logInUser(emailEditText.text.toString(),passwordEditText.text.toString())
                }
        }
    }
    private fun checkField():String? {
        if (emailEditText.text.isNullOrBlank()) {
            return "email is null or blank"
        }
        if (passwordEditText.text.isNullOrBlank()) {
            return "password is null"
        }
        return null
    }
    private  fun logInUser(email:String,password:String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("LogInScreen", "signInWithEmail:success")
                    val user = auth.currentUser
                    user?.let {
                        getUser(user)
                    }
                        ?:run {
                            Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
                        }

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("LogInScreen", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }

    }

    private fun getUser(user: FirebaseUser){
        val db = Firebase.firestore
        db.collection("users").document(user.uid)
            .get().addOnCompleteListener(){
                if(it.isSuccessful){
                    val map=it.result.data
                    map?.let {
                        val userModel= UserModel(map["uid"] as String,map["emailid"] as String,map["phoneNum"] as String,map["name"]as String)
                        AppSharedPref(this).setParam(
                            StringUtils.APP_NAME,
                            StringUtils.IS_LOGGED_IN,true)
                        AppSharedPref(this).setParam(
                            StringUtils.APP_NAME,
                            StringUtils.USER,userModel)
                        startActivity(Intent(this,CategoryScreen::class.java))
                    }

                }
            }
    }
}
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
import com.shrutii.melodrama.databinding.ActivityLogInScreenBinding
import com.shrutii.melodrama.models.UserModel
import com.shrutii.melodrama.utils.AppSharedPref
import com.shrutii.melodrama.utils.StringUtils

class LogInScreen : AppCompatActivity() {
    private lateinit var binding: ActivityLogInScreenBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        click()
        auth = Firebase.auth
    }

    private fun click() {
        binding.signup.setOnClickListener() {
            startActivity(Intent(this, SignUp::class.java))
        }
        binding.button.setOnClickListener() {
            val isValid = checkField()
            isValid?.let {
                Toast.makeText(this, isValid, Toast.LENGTH_SHORT).show()
            }
                ?: run() {
                    logInUser(binding.emailidTiet.text.toString(), binding.password.text.toString())
                }
        }
        binding.google.setOnClickListener {
            Toast.makeText(this, "Khud se karo implement", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkField(): String? {
        if (binding.emailidTiet.text.isNullOrBlank()) {
            return "email is null or blank"
        }
        if (binding.password.text.isNullOrBlank()) {
            return "password is null"
        }
        return null
    }

    private fun logInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("LogInScreen", "signInWithEmail:success")
                    val user = auth.currentUser
                    user?.let {
                        getUser(user)
                    }
                        ?: run {
                            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                        }

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("LogInScreen", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }

    }

    private fun getUser(user: FirebaseUser) {
        val db = Firebase.firestore
        db.collection("users").document(user.uid)
            .get().addOnCompleteListener() {
                if (it.isSuccessful) {
                    val map = it.result.data
                    map?.let {
                        val userModel = UserModel(
                            map["uid"] as String,
                            map["emailid"] as String,
                            map["phoneNum"] as String,
                            map["name"] as String
                        )
                        AppSharedPref(this).setParam(
                            StringUtils.APP_NAME,
                            StringUtils.IS_LOGGED_IN, true
                        )
                        AppSharedPref(this).setParam(
                            StringUtils.APP_NAME,
                            StringUtils.USER, userModel
                        )
                        startActivity(Intent(this, CategoryScreen::class.java))
                    }

                }
            }
    }
}
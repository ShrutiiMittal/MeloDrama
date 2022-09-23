package com.shrutii.melodrama.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.shrutii.melodrama.R
import com.shrutii.melodrama.databinding.ActivityLogInScreenBinding
import com.shrutii.melodrama.databinding.ActivitySignUpBinding
import com.shrutii.melodrama.models.UserModel
import com.shrutii.melodrama.utils.AppSharedPref
import com.shrutii.melodrama.utils.StringUtils

class SignUp : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.button.setOnClickListener() {
            val isValid = checkField()

            isValid?.let {
                Toast.makeText(this, isValid, Toast.LENGTH_SHORT).show()
            }
                ?: run() {
                    createUser(
                        binding.emailidTiet.text.toString(),
                        binding.passwordTiet.text.toString()
                    )
                }

        }

        binding.login.setOnClickListener() {
            startActivity(Intent(this, LogInScreen::class.java))
            finish()
        }

    }

    private fun checkField(): String? {
        if (binding.emailidTiet.text.isNullOrBlank()) {
            return "email is null or blank"
        }
        if (binding.passwordTiet.text.isNullOrBlank()) {
            return "password is null"
        }
        if (binding.nameTiet.text.isNullOrBlank()) {
            return "name is null"
        }
        if (binding.passwordCnfTiet.text.isNullOrBlank()) {
            return "confirm password is null"
        }
        if (binding.phoneTiet.text.isNullOrBlank()) {
            return "phone number is null"
        }
        if (binding.passwordTiet.text.toString() != (binding.passwordCnfTiet.text.toString())) {
            return "password didn't match"
        }
        if (binding.phoneTiet.text.toString().length != 10) {
            return "phone number not of 10 digits"
        }
        return null

    }

    private fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SignUp", "createUserWithEmail:success")
                    val user = auth.currentUser
                    user?.let {
                        uploadUser(user)
                    }
                        ?: run {
                            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("SignUP", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun uploadUser(user: FirebaseUser) {
        val userModel = UserModel(
            user.uid,
            user.email.toString(),
            binding.phoneTiet.text.toString(),
            binding.nameTiet.text.toString()
        )
        val db = Firebase.firestore
        db.collection("users").document(user.uid)
            .set(userModel).addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    AppSharedPref(this).setParam(
                        StringUtils.APP_NAME,
                        StringUtils.IS_LOGGED_IN,
                        true
                    )
                    AppSharedPref(this).setParam(StringUtils.APP_NAME, StringUtils.USER, userModel)
                    startActivity(Intent(this, CategoryScreen::class.java))
                } else {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }

    }
}
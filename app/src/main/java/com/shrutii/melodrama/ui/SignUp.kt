package com.shrutii.melodrama.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shrutii.melodrama.R

class SignUp : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var email:EditText
    lateinit var password:EditText
    lateinit var confirmPassword:EditText
    lateinit var name:EditText
    lateinit var phoneNumber: EditText
    lateinit var signInButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = Firebase.auth
        signInButton=findViewById(R.id.signUpButton)
        email=findViewById(R.id.emailid)
        password=findViewById(R.id.password)
        name=findViewById(R.id.nametext)
        confirmPassword=findViewById(R.id.confirmPssText)
        phoneNumber=findViewById(R.id.MobileText)

        signInButton.setOnClickListener(){
            val isValid= checkField()

            isValid?.let {
                Toast.makeText(this,isValid,Toast.LENGTH_SHORT).show()
            }
                ?:run(){
                    createUser(email.text.toString(),password.text.toString())
                }

        }

    }
    private fun checkField():String?{
        if(email.text.isNullOrBlank())
        {
            return "email is null or blank"
        }
        if(password.text.isNullOrBlank())
        {
            return "password is null"
        }
        if(name.text.isNullOrBlank())
        {
            return "name is null"
        }
        if(confirmPassword.text.isNullOrBlank())
        {
            return "confirm password is null"
        }
        if(phoneNumber.text.isNullOrBlank())
        {
            return "phone number is null"
        }
        if(!password.text.equals(confirmPassword.text))
        {
            return "password didn't match"
        }
        if(phoneNumber.text.length!=10) {
            return "phone number not of 10 digits"
        }
        return null

    }
    private fun createUser(email:String,password:String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SignUp", "createUserWithEmail:success")
                    val user = auth.currentUser

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("SignUP", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}
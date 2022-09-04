package com.shrutii.melodrama

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shrutii.melodrama.ui.LogInScreen
import com.shrutii.melodrama.ui.SignUp
import com.shrutii.melodrama.utils.AppSharedPref
import com.shrutii.melodrama.utils.StringUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkForLogIn(isLoggedIn= AppSharedPref(this).getParam(StringUtils.APP_NAME,StringUtils.EMAIL,false )as Boolean?)
    }
    private fun checkForLogIn(isLoggedIn:Boolean?=null){
        isLoggedIn?.let {
            if(!isLoggedIn) {
                startActivity(Intent(this, LogInScreen::class.java))
            }
            else
                startActivity(Intent(this,MainActivity::class.java))
        }?:run {
            startActivity(Intent(this, LogInScreen::class.java))
        }



    }
}
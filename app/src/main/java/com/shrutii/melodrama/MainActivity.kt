package com.shrutii.melodrama

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shrutii.melodrama.ui.CategoryScreen
import com.shrutii.melodrama.ui.LogInScreen
import com.shrutii.melodrama.ui.SignUp
import com.shrutii.melodrama.utils.AppSharedPref
import com.shrutii.melodrama.utils.BaseActivity
import com.shrutii.melodrama.utils.StringUtils

open class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkForLogIn(
            isLoggedIn = AppSharedPref(this).getParam(
                StringUtils.APP_NAME,
                StringUtils.IS_LOGGED_IN,
                false
            ) as Boolean?
        )
    }

    private fun checkForLogIn(isLoggedIn: Boolean? = null) {
        isLoggedIn?.let {
            if (!isLoggedIn) {
                startActivity(Intent(this, LogInScreen::class.java))
            } else
                startActivity(Intent(this, CategoryScreen::class.java))
        } ?: run {
            startActivity(Intent(this, LogInScreen::class.java))
        }
        finish()
    }

}
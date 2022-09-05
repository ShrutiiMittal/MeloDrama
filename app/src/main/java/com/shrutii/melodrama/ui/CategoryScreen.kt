package com.shrutii.melodrama.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.shrutii.melodrama.R
import com.shrutii.melodrama.models.SongModel


class CategoryScreen : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var songList: ArrayList<SongModel?>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_screen)
        getData()

    }

    private fun getData() {
        database = Firebase.database.reference
        database.child("Sheet1").get().addOnCompleteListener {
            songList = arrayListOf()
            for (activitySnapShot in it.result.children) {
                val receivedData: SongModel? =
                    activitySnapShot.getValue(SongModel::class.java)
                songList.add((receivedData))
            }

            Log.wtf("sm/successful",songList.toString())

        }


    }
}
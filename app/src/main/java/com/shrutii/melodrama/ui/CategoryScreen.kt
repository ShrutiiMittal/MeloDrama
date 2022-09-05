package com.shrutii.melodrama.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.shrutii.melodrama.R
import com.shrutii.melodrama.models.SongModel

class CategoryScreen : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var songList: ArrayList<SongModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_screen)
        getData()

    }
    private fun getData(){
        database = Firebase.database.reference
        database.child("Sheet1").get().addOnCompleteListener{
            if(it.isSuccessful){
                songList= arrayListOf()
              for(child in it.result.children)
              {
                  val currentSong= it.result.child(child.key.toString()).value as Map<String,Object>
                  songList.add( SongModel(currentSong["Song"].toString()))
              }
               Log.wtf("sm/successful",songList.toString())
            }
            else
                Log.wtf("sm/unsuccessful",it.result.toString())
        }
    }
}
package com.shrutii.melodrama.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.shrutii.melodrama.R
import com.shrutii.melodrama.adapter.CategoryFeedAdapter
import com.shrutii.melodrama.databinding.ActivityCategoryScreenBinding
import com.shrutii.melodrama.databinding.CategoryCategorylistBinding
import com.shrutii.melodrama.models.SongFeedModel
import com.shrutii.melodrama.models.SongModel
import com.shrutii.melodrama.utils.AppSharedPref


class CategoryScreen : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var vibeMap: HashMap<String, ArrayList<SongModel>>

    private lateinit var songList: ArrayList<SongFeedModel>
    private lateinit var binding: ActivityCategoryScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData()
        setOnClick()
    }

    private fun setOnClick() {
        binding.logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            AppSharedPref(this).clear()
            startActivity(Intent(this, LogInScreen::class.java))
            finish()
        }
    }

    private fun getData() {
        binding.progressCircular.visibility = View.VISIBLE
        database = Firebase.database.reference
        database.child("Sheet1").get().addOnCompleteListener {
            binding.progressCircular.visibility = View.GONE
            vibeMap = hashMapOf()
            for (activitySnapShot in it.result.children) {
                val receivedData: SongModel? =
                    activitySnapShot.getValue(SongModel::class.java)
                Log.e("sm/SongModel", receivedData.toString())
                val vibeData = vibeMap.get(receivedData?.Vibe)
                vibeData?.let {
                    if (receivedData != null) {
                        it.add(receivedData)
                        receivedData.Vibe?.let { it1 -> vibeMap.put(it1, it) }
                    }
                }
                    ?: run {
                        receivedData?.Vibe?.let { it1 ->
                            arrayListOf(receivedData).let { it2 ->
                                vibeMap.put(
                                    it1,
                                    it2
                                )
                            }
                        }
                    }
            }
            parseData()
            Log.wtf("sm/successful", vibeMap.toString())
        }
    }

    private fun initRV() {
        binding.recyclerView.adapter = CategoryFeedAdapter(this, songList) {

        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.hasFixedSize()
    }

    private fun parseData() {
        songList = arrayListOf()
        for (category in vibeMap) {
            songList.add(SongFeedModel(category.key))
            songList.add(SongFeedModel("", category.value, true))
        }
        initRV()
    }

}
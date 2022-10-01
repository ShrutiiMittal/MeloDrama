package com.shrutii.melodrama.ui

import android.media.AudioManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.shrutii.melodrama.R
import com.shrutii.melodrama.models.SongModel
import com.shrutii.melodrama.utils.BaseActivity
import com.shrutii.melodrama.utils.BasePlayerObject
import java.io.IOException


class PlayerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        val intent = this.intent
        val bundle = intent.extras
        val songModel = bundle!!.getSerializable("songData") as? SongModel
        songModel?.Links?.let {
            playAudio(it)
        }
    }

    private fun playAudio(url : String) {
        val playableLink = getPlayableLink(url)
        BasePlayerObject.mediaPlayer.reset()
        BasePlayerObject.mediaPlayer.seekTo(0)
        BasePlayerObject.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        try {
            BasePlayerObject.mediaPlayer.setDataSource(playableLink)
            BasePlayerObject.mediaPlayer.prepare()
            BasePlayerObject.mediaPlayer.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        Toast.makeText(this, "Audio started playing..", Toast.LENGTH_SHORT).show()
    }

    private fun getPlayableLink(url: String) : String {
        val id = getSongID(url)
        return "https://drive.google.com/uc?export=download&id=$id"
    }

    private fun getSongID(url : String) : String{
        return url.split("/")[5]
    }

}
package com.esgi.yfitops

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.albums).setOnClickListener {
            val i = Intent(this, AlbumActivity::class.java)
            startActivity(i)
        }
        findViewById<Button>(R.id.classement).setOnClickListener {
            val i = Intent(this, RankingActivity::class.java)
            startActivity(i)
        }
        findViewById<Button>(R.id.artists).setOnClickListener {
            val i = Intent(this, ArtistActivity::class.java)
            startActivity(i)
        }
    }

}
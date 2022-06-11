package com.esgi.yfitops

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.esgi.yfitops.models.services.TrackService

class RankingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)
        TrackService().getRanksTrack(this).thenAccept { response ->

        }
    }
}
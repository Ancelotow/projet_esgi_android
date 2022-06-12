package com.esgi.yfitops

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.yfitops.models.entities.Track
import com.esgi.yfitops.models.services.TrackService
import com.squareup.picasso.Picasso

class RankingActivity : AppCompatActivity() {

    var listTracks = mutableListOf<Track>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.adapter = ListAdapter(listTracks)
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        TrackService().getRanksTrack(this).thenAccept { response ->
            listTracks = response
            recyclerView.adapter = ListAdapter(listTracks)
            recyclerView.layoutManager = GridLayoutManager(this, 1)
        }
    }

}


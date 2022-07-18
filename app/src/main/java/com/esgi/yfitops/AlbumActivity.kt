package com.esgi.yfitops

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.yfitops.models.entities.AlbumDetail
import com.esgi.yfitops.models.entities.Track
import com.esgi.yfitops.models.repositories.AlbumDetailStateError
import com.esgi.yfitops.models.repositories.AlbumDetailStateLoading
import com.esgi.yfitops.models.repositories.AlbumDetailStateSuccess
import com.esgi.yfitops.viewModel.AlbumViewModel
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Picasso
import kotlin.properties.Delegates


class AlbumActivity : AppCompatActivity() {
    private val viewModel: AlbumViewModel by viewModels()
    private var idAlbum by Delegates.notNull<Int>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        idAlbum = intent.getIntExtra("idAlbum", 0)
        viewModel.getAlbumId(idAlbum)

        val btnBack = findViewById<MaterialButton>(R.id.btn_back)
        btnBack.setOnClickListener { finish() }

        val loader = findViewById<ProgressBar>(R.id.loader_info)
        val layoutError = findViewById<ConstraintLayout>(R.id.layout_error)
        val layoutAlbum = findViewById<NestedScrollView>(R.id.layout_track)
        loader.visibility = View.VISIBLE
        layoutAlbum.visibility = View.GONE
        layoutError.visibility = View.GONE
        viewModel.album.observe(this) {
            when (it) {
                is AlbumDetailStateError -> {
                    loader.visibility = View.GONE
                    layoutError.visibility = View.VISIBLE
                }
                AlbumDetailStateLoading -> {
                    loader.visibility = View.VISIBLE
                    layoutAlbum.visibility = View.GONE
                    layoutError.visibility = View.GONE
                }
                is AlbumDetailStateSuccess -> {
                    loader.visibility = View.GONE
                    layoutError.visibility = View.GONE
                    layoutAlbum.visibility = View.VISIBLE
                    if(it.album != null) {
                        displayInformation(it.album!!)
                    }
                }
            }
        }
    }

    private fun displayInformation(album: AlbumDetail) {

        findViewById<TextView>(R.id.album_name).text = album.information.name

        val imageAlbum = findViewById<ImageView>(R.id.album_image)
        val imageDetailAlbum = findViewById<ImageView>(R.id.detail_image)
        val thumbAlbum = album.information.thumb
        if(thumbAlbum != null && thumbAlbum.isNotEmpty()) {
            Picasso.get().load(thumbAlbum).into(imageAlbum)
            Picasso.get().load(thumbAlbum).into(imageDetailAlbum)
        }


        val nameArtist =  album.information.artist
        findViewById<TextView>(R.id.artist_name).text = nameArtist

        val noteVote =  album.information.score ?: 0
        findViewById<TextView>(R.id.vote_album_int).text = noteVote.toString()

        val nbVote =  album.information.scoreVotes ?: 0
        findViewById<TextView>(R.id.vote_album).text = nbVote.toString()

        val description =  album.information.descriptionEN
        findViewById<TextView>(R.id.description).text = description

        if(album.tracks.track != null) {
            val recyclerViewTrack = findViewById<RecyclerView>(R.id.recyclerview_album)
            recyclerViewTrack.adapter = ListAdapterTrackAlbum(album.tracks.track as MutableList<Track>)
            recyclerViewTrack.layoutManager = GridLayoutManager(this, 1)
        }

        val nbTrack = album.tracks.track?.size ?: 0
        findViewById<TextView>(R.id.album_song_nb).text = getString(R.string.txt_music_nb, nbTrack.toString())



    }

}

class ListAdapterTrackAlbum(private val tracks: MutableList<Track>) : RecyclerView.Adapter<TrackAlbumViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackAlbumViewHolder {
        return TrackAlbumViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_track, parent, false)
        ).listen()
    }

    override fun onBindViewHolder(holder: TrackAlbumViewHolder, position: Int) {
        holder.setItem(tracks[position], position + 1)
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    private fun <ListAdapterTrackAlbum : RecyclerView.ViewHolder> ListAdapterTrackAlbum.listen(): ListAdapterTrackAlbum {
        itemView.setOnClickListener {
            val track = tracks[adapterPosition]
            val intent = Intent(it.context, LyricsActivity::class.java)
            intent.putExtra("idTrack", track.id)
            it.context.startActivity(intent)
        }
        return this
    }

}

class TrackAlbumViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    private val trackNumber = v.findViewById<TextView>(R.id.number)
    private val trackTitle = v.findViewById<TextView>(R.id.title_track)

    fun setItem(item: Track, rank: Int) {
        trackNumber.text = rank.toString()
        trackTitle.text = item.title
    }
}
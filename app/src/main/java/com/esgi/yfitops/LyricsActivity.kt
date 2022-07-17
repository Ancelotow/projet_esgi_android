package com.esgi.yfitops

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import com.esgi.yfitops.models.entities.Track
import com.esgi.yfitops.models.repositories.*
import com.esgi.yfitops.viewModel.LyricViewModel
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Picasso
import kotlin.properties.Delegates

class LyricsActivity : AppCompatActivity() {

    private val viewModel: LyricViewModel by viewModels()
    private var idTrack by Delegates.notNull<Int>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lyrics)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        idTrack = intent.getIntExtra("idTrack", 0)
        viewModel.getTrack(idTrack)

        val btnBack = findViewById<MaterialButton>(R.id.btn_back)
        btnBack.setOnClickListener { finish() }

        val loader = findViewById<ProgressBar>(R.id.loader_lyric)
        val layoutError = findViewById<ConstraintLayout>(R.id.layout_error)
        val layoutLyrics = findViewById<NestedScrollView>(R.id.layout_lyrics)

        val btnErrorRetry = layoutError.findViewById<MaterialButton>(R.id.btn_error_retry)
        btnErrorRetry.setOnClickListener { viewModel.getTrack(idTrack) }

        loader.visibility = View.VISIBLE
        layoutLyrics.visibility = View.GONE
        layoutError.visibility = View.GONE
        viewModel.track.observe(this) {
            when (it) {
                is LyricStateError -> {
                    loader.visibility = View.GONE
                    layoutError.visibility = View.VISIBLE
                }
                LyricStateLoading -> {
                    loader.visibility = View.VISIBLE
                    layoutLyrics.visibility = View.GONE
                    layoutError.visibility = View.GONE
                }
                is LyricStateSuccess -> {
                    loader.visibility = View.GONE
                    layoutError.visibility = View.GONE
                    layoutLyrics.visibility = View.VISIBLE
                    if(it.track != null) {
                        displayInformation(it.track)
                    }
                }
            }
        }
    }

    private fun displayInformation(track: Track) {
        findViewById<TextView>(R.id.track_name).text = track.title

        val coverAlbum = findViewById<ImageView>(R.id.cover_album)
        val coverAlbumBg = findViewById<ImageView>(R.id.cover_album_bg)
        val thumbTrack = track.thumb
        if(thumbTrack != null && thumbTrack.isNotEmpty()) {
            Picasso.get().load(thumbTrack).into(coverAlbum)
            Picasso.get().load(thumbTrack).into(coverAlbumBg)
        }

        val lyrics = if (track.lyrics == null || track.lyrics!!.isEmpty()) {
            getString(R.string.txt_no_lyrics)
        } else {
            track.lyrics!!
        }
        findViewById<TextView>(R.id.lyrics).text = lyrics

    }

}
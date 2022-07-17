package com.esgi.yfitops

import android.os.Bundle
import android.view.View
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
import com.esgi.yfitops.models.entities.Artist
import com.esgi.yfitops.models.entities.ArtistDetail
import com.esgi.yfitops.models.repositories.*
import com.esgi.yfitops.viewModel.ArtistViewModel
import com.squareup.picasso.Picasso
import kotlin.properties.Delegates

class ArtistActivity : AppCompatActivity() {

    private val viewModel: ArtistViewModel by viewModels()
    private var idArtist by Delegates.notNull<Int>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artist)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        idArtist = intent.getIntExtra("idArtist", 0)
        viewModel.getArtist(idArtist)

        val loader = findViewById<ProgressBar>(R.id.loader_info)
        val layoutError = findViewById<ConstraintLayout>(R.id.layout_error)
        val layoutArtist = findViewById<NestedScrollView>(R.id.layout_artist)
        loader.visibility = View.VISIBLE
        layoutArtist.visibility = View.GONE
        layoutError.visibility = View.GONE
        viewModel.artist.observe(this) {
            when (it) {
                is ArtistStateError -> {
                    loader.visibility = View.GONE
                    layoutError.visibility = View.VISIBLE
                }
                ArtistStateLoading -> {
                    loader.visibility = View.VISIBLE
                    layoutArtist.visibility = View.GONE
                    layoutError.visibility = View.GONE
                }
                is ArtistStateSuccess -> {
                    loader.visibility = View.GONE
                    layoutError.visibility = View.GONE
                    layoutArtist.visibility = View.VISIBLE
                    if(it.artist != null) {
                        displayInformation(it.artist)
                    }
                }
            }
        }
    }

    private fun displayInformation(artist: ArtistDetail) {
        findViewById<TextView>(R.id.artist_name).text = artist.information.nameArtist
        val imageArtist = findViewById<ImageView>(R.id.artist_image)
        val thumbArtist = artist.information.thumb
        if(thumbArtist != null && thumbArtist.isNotEmpty()) {
            Picasso.get().load(thumbArtist).into(imageArtist)
        }

        val artistFrom =  artist.information.country
        val genre =  artist.information.genre
        findViewById<TextView>(R.id.artist_from_genre).text = "$artistFrom - $genre"
        findViewById<TextView>(R.id.description).text = artist.information.getDescription()

        val albumLabel = getString(R.string.search_albums)
        val nbAlbum = artist.albums.album?.size ?: 0
        findViewById<TextView>(R.id.album).text = "$albumLabel (${nbAlbum})"
    }

}



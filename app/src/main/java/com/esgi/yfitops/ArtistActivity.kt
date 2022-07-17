package com.esgi.yfitops

import android.content.Intent
import android.os.Bundle
import android.view.*
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
import com.esgi.yfitops.models.entities.Album
import com.esgi.yfitops.models.entities.ArtistDetail
import com.esgi.yfitops.models.entities.Track
import com.esgi.yfitops.models.repositories.*
import com.esgi.yfitops.viewModel.ArtistViewModel
import com.google.android.material.button.MaterialButton
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
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        idArtist = intent.getIntExtra("idArtist", 0)
        viewModel.getArtist(idArtist)

        val btnBack = findViewById<MaterialButton>(R.id.btn_back)
        btnBack.setOnClickListener { finish() }

        val loader = findViewById<ProgressBar>(R.id.loader_info)
        val layoutError = findViewById<ConstraintLayout>(R.id.layout_error)
        val layoutArtist = findViewById<NestedScrollView>(R.id.layout_artist)

        val btnErrorRetry = layoutError.findViewById<MaterialButton>(R.id.btn_error_retry)
        btnErrorRetry.setOnClickListener { viewModel.getArtist(idArtist) }

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

        if(nbAlbum > 0) {
            val recyclerViewAlbum = findViewById<RecyclerView>(R.id.recyclerview_album)
            recyclerViewAlbum.adapter = ListAdapterAlbumArtist(artist.albums.album as MutableList<Album>)
            recyclerViewAlbum.layoutManager = GridLayoutManager(this, 1)
        }

        if(artist.topTracks.track != null) {
            val recyclerViewTrack = findViewById<RecyclerView>(R.id.recyclerview_track)
            recyclerViewTrack.adapter = ListAdapterTrackArtist(artist.topTracks.track as MutableList<Track>)
            recyclerViewTrack.layoutManager = GridLayoutManager(this, 1)
        }

    }

}


class ListAdapterAlbumArtist(private val albums: MutableList<Album>) : RecyclerView.Adapter<AlbumArtistViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumArtistViewHolder {
        return AlbumArtistViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_album, parent, false)
        ).listen()
    }

    override fun onBindViewHolder(holder: AlbumArtistViewHolder, position: Int) {
        holder.setItem(albums[position])
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    private fun <ListAdapterAlbumArtist : RecyclerView.ViewHolder> ListAdapterAlbumArtist.listen(): ListAdapterAlbumArtist {
        itemView.setOnClickListener {
            val album = albums[adapterPosition]
            val intent = Intent(it.context, AlbumActivity::class.java)
            intent.putExtra("idAlbum", album.id)
            it.context.startActivity(intent)
        }
        return this
    }

}

class ListAdapterTrackArtist(private val tracks: MutableList<Track>) : RecyclerView.Adapter<TrackArtistViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackArtistViewHolder {
        return TrackArtistViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_track, parent, false)
        ).listen()
    }

    override fun onBindViewHolder(holder: TrackArtistViewHolder, position: Int) {
        holder.setItem(tracks[position], position + 1)
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    private fun <ListAdapterTrackArtist : RecyclerView.ViewHolder> ListAdapterTrackArtist.listen(): ListAdapterTrackArtist {
        itemView.setOnClickListener {
            val track = tracks[adapterPosition]
            val intent = Intent(it.context, LyricsActivity::class.java)
            intent.putExtra("idTrack", track.id)
            it.context.startActivity(intent)
        }
        return this
    }

}

class AlbumArtistViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    private val albumThumb = v.findViewById<ImageView>(R.id.picture_album_logo)
    private val albumTitle = v.findViewById<TextView>(R.id.title_album)
    private val albumReleased = v.findViewById<TextView>(R.id.desc_album)

    fun setItem(item: Album) {
        albumTitle.text = item.name
        albumReleased.text = item.yearReleased.toString()
        if(item.thumb != null && item.thumb!!.isNotEmpty()) {
            Picasso.get().load(item.thumb).into(albumThumb)
        }
    }
}

class TrackArtistViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    private val trackNumber = v.findViewById<TextView>(R.id.number)
    private val trackTitle = v.findViewById<TextView>(R.id.title_track)

    fun setItem(item: Track, rank: Int) {
        trackNumber.text = rank.toString()
        trackTitle.text = item.title
    }
}



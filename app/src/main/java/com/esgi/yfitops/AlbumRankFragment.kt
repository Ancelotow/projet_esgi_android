package com.esgi.yfitops

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.yfitops.models.entities.Album
import com.esgi.yfitops.models.repositories.AlbumStateError
import com.esgi.yfitops.models.repositories.AlbumStateLoading
import com.esgi.yfitops.models.repositories.AlbumStateSuccess
import com.esgi.yfitops.viewModel.AlbumViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import com.squareup.picasso.Picasso


class AlbumRankFragment : Fragment() {

    val viewModel: AlbumViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_album_rank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val shimmer_layout = view.findViewById<ShimmerFrameLayout>(R.id.shimmer_layout)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        shimmer_layout.visibility = View.VISIBLE
        viewModel.listAlbums.observe(viewLifecycleOwner) {
            when (it) {
                is AlbumStateError -> {
                    shimmer_layout.visibility = View.GONE
                }
                AlbumStateLoading -> {
                    shimmer_layout.visibility = View.VISIBLE
                }
                is AlbumStateSuccess -> {
                    shimmer_layout.visibility = View.GONE
                    recyclerView.adapter = ListAdapterAlbum(it.albums as MutableList<Album>)
                    recyclerView.layoutManager = GridLayoutManager(context, 1)
                }
            }
        }
    }

}

class ListAdapterAlbum(val albums: MutableList<Album>) : RecyclerView.Adapter<AlbumViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_rank, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.setItem(albums[position], (position + 1))
    }

    override fun getItemCount(): Int {
        return albums.size
    }

}


class AlbumViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    private val albumRank = v.findViewById<TextView>(R.id.rank)
    private val albumThumb = v.findViewById<ImageView>(R.id.thumb)
    private val albumTitle = v.findViewById<TextView>(R.id.title)
    private val albumArtist = v.findViewById<TextView>(R.id.artist)

    fun setItem(item: Album, rank: Int) {
        albumRank.text = rank.toString()
        albumTitle.text = item.name
        albumArtist.text = item.artist
        Picasso.get().load(item.thumb).into(albumThumb)
    }
}
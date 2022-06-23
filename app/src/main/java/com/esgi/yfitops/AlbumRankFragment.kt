package com.esgi.yfitops

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.yfitops.models.entities.Album
import com.esgi.yfitops.models.entities.Album.Service.getAlbumsRanks
import com.facebook.shimmer.ShimmerFrameLayout
import com.squareup.picasso.Picasso
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AlbumRankFragment : Fragment() {

    var listAlbums = mutableListOf<Album>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var shimmerLayout: ShimmerFrameLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_album_rank, container, false)
        shimmerLayout = view.findViewById(R.id.shimmer_layout)
        this.recyclerView = view.findViewById(R.id.recyclerview)
        recyclerView.adapter = ListAdapterAlbum(listAlbums)
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        initRecyclerView()
        return view
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun initRecyclerView() {
        shimmerLayout.visibility = View.VISIBLE // Met en visible le miroitement
        shimmerLayout.startShimmer() // Démarre le shimmer (l'effet de miroitement)
        GlobalScope.launch(Dispatchers.Default) {
            listAlbums = getAlbumsRanks() as MutableList<Album>
            GlobalScope.launch(Dispatchers.Main) {
                recyclerView.adapter = ListAdapterAlbum(listAlbums)
                recyclerView.layoutManager = GridLayoutManager(context, 1)
                shimmerLayout.stopShimmer() // arrête le shimmer (l'effet de miroitement)
                shimmerLayout.visibility = View.GONE // cache la liste de shimmer
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
package com.esgi.yfitops

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.yfitops.models.entities.Album
import com.esgi.yfitops.models.repositories.AlbumStateError
import com.esgi.yfitops.models.repositories.AlbumStateLoading
import com.esgi.yfitops.models.repositories.AlbumStateSuccess
import com.esgi.yfitops.viewModel.AlbumRankViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import com.squareup.picasso.Picasso


class AlbumRankFragment : Fragment() {

    val viewModel: AlbumRankViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_album_rank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val shimmerLayout = view.findViewById<ShimmerFrameLayout>(R.id.shimmer_layout)
        val layoutError = view.findViewById<ConstraintLayout>(R.id.layout_error)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        shimmerLayout.visibility = View.VISIBLE
        layoutError.visibility = View.GONE
        viewModel.listAlbums.observe(viewLifecycleOwner) {
            when (it) {
                is AlbumStateError -> {
                    layoutError.visibility = View.VISIBLE
                    shimmerLayout.visibility = View.GONE
                }
                AlbumStateLoading -> {
                    shimmerLayout.visibility = View.VISIBLE
                    layoutError.visibility = View.GONE
                }
                is AlbumStateSuccess -> {
                    shimmerLayout.visibility = View.GONE
                    recyclerView.adapter = ListAdapterAlbum(it.albums as MutableList<Album>)
                    recyclerView.layoutManager = GridLayoutManager(context, 1)
                }
            }
        }
    }

}

class ListAdapterAlbum(val albums: MutableList<Album>) : RecyclerView.Adapter<AlbumRankViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumRankViewHolder {
        return AlbumRankViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_rank, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AlbumRankViewHolder, position: Int) {
        holder.setItem(albums[position], (position + 1))
    }

    override fun getItemCount(): Int {
        return albums.size
    }

}


class AlbumRankViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    private val albumRank = v.findViewById<TextView>(R.id.rank)
    private val albumThumb = v.findViewById<ImageView>(R.id.thumb)
    private val albumTitle = v.findViewById<TextView>(R.id.title)
    private val albumArtist = v.findViewById<TextView>(R.id.artist)

    fun setItem(item: Album, rank: Int) {
        albumRank.text = rank.toString()
        albumTitle.text = item.name
        albumArtist.text = item.artist
        if(item.thumb != null && item.thumb!!.isNotEmpty()) {
            Picasso.get().load(item.thumb).into(albumThumb)
        }
    }
}
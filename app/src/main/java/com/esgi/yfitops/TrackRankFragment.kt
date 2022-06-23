package com.esgi.yfitops

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.yfitops.models.entities.Album
import com.esgi.yfitops.models.entities.Track
import com.esgi.yfitops.models.services.TrackService
import com.facebook.shimmer.ShimmerFrameLayout
import com.squareup.picasso.Picasso
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TrackRankFragment : Fragment() {

    var listTracks = mutableListOf<Track>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var shimmerLayout: ShimmerFrameLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_track_rank, container, false)
        this.recyclerView = view.findViewById(R.id.recyclerview)
        shimmerLayout = view.findViewById(R.id.shimmer_layout)
        initRecyclerView()
        return view
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun initRecyclerView() {
        shimmerLayout.visibility = View.VISIBLE // Met en visible le miroitement
        shimmerLayout.startShimmer() // Démarre le shimmer (l'effet de miroitement)
        GlobalScope.launch(Dispatchers.Default) {
            listTracks = Track.getTrackRank() as MutableList<Track>
            GlobalScope.launch(Dispatchers.Main) {
                recyclerView.adapter = ListAdapterTrack(listTracks)
                recyclerView.layoutManager = GridLayoutManager(context, 1)
                shimmerLayout.stopShimmer() // arrête le shimmer (l'effet de miroitement)
                shimmerLayout.visibility = View.GONE // cache la liste de shimmer
            }
        }
    }

}

class ListAdapterTrack(val tracks: MutableList<Track>) : RecyclerView.Adapter<TrackViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_rank, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.setItem(tracks[position], (position + 1))
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

}


class TrackViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    private val trackRank = v.findViewById<TextView>(R.id.rank)
    private val trackThumb = v.findViewById<ImageView>(R.id.thumb)
    private val trackTitle = v.findViewById<TextView>(R.id.title)
    private val trackArtist = v.findViewById<TextView>(R.id.artist)

    fun setItem(item: Track, rank: Int) {
        trackRank.text = rank.toString()
        trackTitle.text = item.title
        trackArtist.text = item.artist
        Picasso.get().load(item.thumb).into(trackThumb)
    }
}
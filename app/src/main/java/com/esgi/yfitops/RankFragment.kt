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
import com.esgi.yfitops.models.entities.Track
import com.esgi.yfitops.models.services.TrackService
import com.squareup.picasso.Picasso


class RankFragment : Fragment() {

    var listTracks = mutableListOf<Track>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_rank, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.adapter = ListAdapter(listTracks)
        recyclerView.layoutManager = GridLayoutManager(view.context, 1)
        TrackService().getRanksTrack(view.context).thenAccept { response ->
            listTracks = response
            recyclerView.adapter = ListAdapter(listTracks)
            recyclerView.layoutManager = GridLayoutManager(view.context, 1)
        }
        return view
    }


}

class ListAdapter(val tracks: List<Track>) : RecyclerView.Adapter<TrackViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_track, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.setTrack(tracks[position], (position + 1))
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

    fun setTrack(track: Track, rank: Int) {
        trackRank.setText(rank.toString())
        trackTitle.setText(track.title)
        trackArtist.setText(track.artist)
        Picasso.get().load(track.thumb).into(trackThumb)
    }

}
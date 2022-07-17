package com.esgi.yfitops

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.yfitops.models.entities.Track
import com.esgi.yfitops.models.repositories.*
import com.esgi.yfitops.viewModel.TrackRankViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Picasso

class TrackRankFragment : Fragment() {

    private val viewModel: TrackRankViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_track_rank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val shimmerLayout = view.findViewById<ShimmerFrameLayout>(R.id.shimmer_layout)
        val layoutError = view.findViewById<ConstraintLayout>(R.id.layout_error)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)

        val btnErrorRetry = layoutError.findViewById<MaterialButton>(R.id.btn_error_retry)
        btnErrorRetry.setOnClickListener { viewModel.getTracksRank() }

        shimmerLayout.visibility = View.VISIBLE
        layoutError.visibility = View.GONE
        viewModel.listTrack.observe(viewLifecycleOwner) {
            when (it) {
                is TrackStateError -> {
                    shimmerLayout.visibility = View.GONE
                    layoutError.visibility = View.VISIBLE
                }
                TrackStateLoading -> {
                    shimmerLayout.visibility = View.VISIBLE
                    layoutError.visibility = View.GONE
                }
                is TrackStateSuccess -> {
                    shimmerLayout.visibility = View.GONE
                    recyclerView.adapter = ListAdapterTrack(it.tracks as MutableList<Track>)
                    recyclerView.layoutManager = GridLayoutManager(context, 1)
                }
            }
        }
    }

}

class ListAdapterTrack(val tracks: MutableList<Track>) : RecyclerView.Adapter<TrackViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_rank, parent, false)
        ).listen()
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.setItem(tracks[position], (position + 1))
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    private fun <ListAdapterTrack : RecyclerView.ViewHolder> ListAdapterTrack.listen(): ListAdapterTrack {
        itemView.setOnClickListener {
            val track = tracks[adapterPosition]
            val intent = Intent(it.context, LyricsActivity::class.java)
            intent.putExtra("idTrack", track.id)
            it.context.startActivity(intent)
        }
        return this
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
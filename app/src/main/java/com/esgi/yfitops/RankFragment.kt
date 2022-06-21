package com.esgi.yfitops

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.yfitops.models.entities.Rank
import com.esgi.yfitops.models.services.AlbumService
import com.esgi.yfitops.models.services.TrackService
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso


class RankFragment : Fragment() {

    var listItems = mutableListOf<Rank>()
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_rank, container, false)
        this.recyclerView = view.findViewById(R.id.recyclerview)
        recyclerView.adapter = ListAdapter(listItems)
        recyclerView.layoutManager = GridLayoutManager(view.context, 1)
        val tabs = view.findViewById<TabLayout>(R.id.tabs)
        tabs.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab : TabLayout.Tab) {
                when (tab.position) {
                    0 -> initRankTrack()
                    1 -> initRankAlbum()
                }
            }
            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }
        })
        initRankTrack()
        return view
    }


    private fun clearList() {
        listItems.clear()
        refreshRecyclerView()
    }

    private fun refreshRecyclerView() {
        recyclerView.adapter = ListAdapter(listItems)
        recyclerView.layoutManager = GridLayoutManager(context, 1)
    }

    private fun initRankTrack() {
        clearList()
        context?.let { it1 ->
            TrackService().getRanksTrack(it1).thenAccept { response ->
                listItems = response.map { e -> e.toRank() }.toMutableList()
                refreshRecyclerView()
            }
        }
    }

    private fun initRankAlbum() {
        clearList()
        context?.let {it ->
            listItems.clear()
            AlbumService().getRanksAlbum(it).thenAccept { response ->
                listItems = response.map { e -> e.toRank() }.toMutableList()
                refreshRecyclerView()
            }
        }
    }




}

class ListAdapter(val items: MutableList<Rank>) : RecyclerView.Adapter<TrackViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_rank, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.setItem(items[position], (position + 1))
    }

    override fun getItemCount(): Int {
        return items.size
    }

}

class TrackViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    private val itemRank = v.findViewById<TextView>(R.id.rank)
    private val itemThumb = v.findViewById<ImageView>(R.id.thumb)
    private val itemTitle = v.findViewById<TextView>(R.id.title)
    private val itemArtist = v.findViewById<TextView>(R.id.artist)

    fun setItem(item: Rank, rank: Int) {
        itemRank.text = rank.toString()
        itemTitle.text = item.title
        itemArtist.text = item.subTitle
        Picasso.get().load(item.thumb).into(itemThumb)
    }

}
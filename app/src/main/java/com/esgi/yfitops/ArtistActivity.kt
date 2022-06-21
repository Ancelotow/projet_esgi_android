package com.esgi.yfitops

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.yfitops.models.entities.AlbumDet
import com.esgi.yfitops.models.services.AlbumService

class ArtistActivity : AppCompatActivity() {

    private lateinit var picturePP: ImageView
    private lateinit var titleA: TextView
    private lateinit var fromA: TextView
    private lateinit var descriptionA: TextView

    private var listItemsAl = mutableListOf<AlbumDet>()
    private lateinit var recyclerViewAl: RecyclerView

    private var listItemsTitle = mutableListOf<AlbumDet>()
    private lateinit var recyclerViewTitle: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_artist)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        this.picturePP = findViewById(R.id.pp_art)
        this.titleA = findViewById(R.id.title)
        this.fromA = findViewById(R.id.from)
        this.descriptionA = findViewById(R.id.description)


        this.recyclerViewAl = findViewById(R.id.recyclerview_album)
        recyclerViewAl.adapter = ListAdapterAl(listItemsAl)
        recyclerViewAl.layoutManager = GridLayoutManager(this, 1)
        initAlbumId()

        this.recyclerViewTitle = findViewById(R.id.recyclerview_title)
        recyclerViewTitle.adapter = ListAdapterT(listItemsTitle)
        recyclerViewTitle.layoutManager = GridLayoutManager(this, 1)
        initTrackId()

    }


    private fun clearListAl() {
        listItemsAl.clear()
        refreshRecyclerViewAl()
    }

    private fun refreshRecyclerViewAl() {
        recyclerViewAl.adapter = ListAdapterAl(listItemsAl)
        recyclerViewAl.layoutManager = GridLayoutManager(this, 1)
    }

    private fun initAlbumId() {
        clearListAl()
        this?.let {it ->
            listItemsAl.clear()
            AlbumService().getAlbumId(it,147737).thenAccept { response ->
                listItemsAl = response.map { e -> e.toAlbumDet() }.toMutableList()
                refreshRecyclerViewAl()
            }
        }
    }

    private fun clearListT() {
        listItemsTitle.clear()
        refreshRecyclerViewT()
    }

    private fun refreshRecyclerViewT() {
        recyclerViewTitle.adapter = ListAdapterT(listItemsTitle)
        recyclerViewTitle.layoutManager = GridLayoutManager(this, 1)
    }

    private fun initTrackId() {
        clearListT()
        this?.let { it1 ->
            AlbumService().getAlbumId(it1,147737).thenAccept { response ->
                listItemsTitle = response.map { e -> e.toAlbumDet() }.toMutableList()
                refreshRecyclerViewT()
            }
        }
    }

}

class ListAdapterAl(val items: MutableList<AlbumDet>) : RecyclerView.Adapter<TrackViewHolderAl>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolderAl {
        return TrackViewHolderAl(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_album, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TrackViewHolderAl, position: Int) {
        holder.setItem(items[position], (position + 1))
    }

    override fun getItemCount(): Int {
        return items.size
    }

}

class ListAdapterT(val items: MutableList<AlbumDet>) : RecyclerView.Adapter<TrackViewHolderT>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolderT {
        return TrackViewHolderT(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_titre, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TrackViewHolderT, position: Int) {
        holder.setItem(items[position], (position + 1))
    }

}


/*
    class TrackViewHolderAl(v: View) : RecyclerView.ViewHolder(v) {

        private val titleAl = v.findViewById<TextView>(R.id.title_album)
        private val descAl = v.findViewById<TextView>(R.id.desc)

        fun setItem(item: Rank) {
            titleAl.text = item.title
            descAl.text = item.dateY
        }

    }
*/

class TrackViewHolderAl(v: View) : RecyclerView.ViewHolder(v) {

    private val itemTitle= v.findViewById<TextView>(R.id.title_album)
    private val itemdesc= v.findViewById<TextView>(R.id.desc)

    fun setItem(item: AlbumDet, rank: Int) {
        itemTitle.text = item.title
        itemdesc.text = item.dateY
    }

}

class TrackViewHolderT(v: View) : RecyclerView.ViewHolder(v) {

    private val itemN= v.findViewById<TextView>(R.id.number)
    private val itemTitle= v.findViewById<TextView>(R.id.title_track)

    fun setItem(item: AlbumDet, rank: Int) {
        itemN.text = rank.toString()
        itemTitle.text = item.title
    }

}



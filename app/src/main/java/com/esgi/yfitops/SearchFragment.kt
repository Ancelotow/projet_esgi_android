package com.esgi.yfitops

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.yfitops.models.entities.Album
import com.esgi.yfitops.models.entities.Artist
import com.esgi.yfitops.models.enums.ESearchType
import com.esgi.yfitops.models.repositories.*
import com.esgi.yfitops.viewModel.SearchViewModel
import com.squareup.picasso.Picasso

class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var searchField: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchField = view.findViewById(R.id.search_text)
        searchField.setOnKeyListener { v, keyCode, event ->
            viewModel.getSearch(searchField.text.toString())
            return@setOnKeyListener false
        }
        val loaderList = view.findViewById<ProgressBar>(R.id.loader_list)
        val layoutError = view.findViewById<ConstraintLayout>(R.id.layout_error)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        loaderList.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        viewModel.search.observe(viewLifecycleOwner) {
            when (it) {
                is SearchStateError -> {
                    loaderList.visibility = View.GONE
                    layoutError.visibility = View.VISIBLE
                }
                SearchStateLoading -> {
                    loaderList.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                is SearchStateSuccess -> {
                    loaderList.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    recyclerView.adapter = ListAdapterSearch(it.search.getListResult(this@SearchFragment.context!!))
                    recyclerView.layoutManager = GridLayoutManager(context, 1)
                }
            }
        }
    }

}

class ListAdapterSearch(private val searchResult: MutableList<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType ==  ESearchType.ALBUM.ordinal) {
            AlbumViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_album, parent, false)
            )
        } else if(viewType ==  ESearchType.ARTIST.ordinal) {
            ArtistViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_artist, parent, false)
            )
        } else if(viewType ==  ESearchType.HEADER.ordinal) {
            HeaderViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.header_recycle_view, parent, false)
            )
        } else {
            throw Error("Unknown type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        if(viewType ==  ESearchType.ALBUM.ordinal) {
            val album = searchResult[position] as Album
            (holder as AlbumViewHolder).setItem(album)
        } else if(viewType ==  ESearchType.ARTIST.ordinal) {
            val artist = searchResult[position] as Artist
            (holder as ArtistViewHolder).setItem(artist)
        } else if(viewType ==  ESearchType.HEADER.ordinal) {
            val title = searchResult[position] as String
            (holder as HeaderViewHolder).setItem(title)
        }
    }

    override fun getItemCount(): Int {
        return searchResult.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(searchResult[position] is Artist) {
            ESearchType.ARTIST.ordinal
        } else if(searchResult[position] is Album) {
            ESearchType.ALBUM.ordinal
        } else if(searchResult[position] is String) {
            ESearchType.HEADER.ordinal
        } else {
            ESearchType.UNKNOWN.ordinal
        }
    }

}

class AlbumViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    private val albumThumb = v.findViewById<ImageView>(R.id.picture_album_logo)
    private val albumTitle = v.findViewById<TextView>(R.id.title_album)
    private val albumArtist = v.findViewById<TextView>(R.id.desc_album)

    fun setItem(item: Album) {
        albumTitle.text = item.name
        albumArtist.text = item.artist
        Picasso.get().load(item.thumb).into(albumThumb)
    }
}

class ArtistViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    private val artistThumb = v.findViewById<ImageView>(R.id.artist_image)
    private val artistName = v.findViewById<TextView>(R.id.artist_name)

    fun setItem(item: Artist) {
        artistName.text = item.artist
        if(item.thumb.isNotEmpty()) {
            Picasso.get().load(item.thumb).into(artistThumb)
        }
    }
}

class HeaderViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    private val headerTitle = v.findViewById<TextView>(R.id.title)

    fun setItem(title: String) {
        headerTitle.text = title
    }
}
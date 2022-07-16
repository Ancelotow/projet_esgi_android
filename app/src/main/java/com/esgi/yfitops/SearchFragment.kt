package com.esgi.yfitops

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.esgi.yfitops.models.repositories.*
import com.esgi.yfitops.viewModel.SearchViewModel
import com.google.android.material.progressindicator.CircularProgressIndicator

class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loaderList = view.findViewById<ProgressBar>(R.id.loader_list)
        val layoutError = view.findViewById<ConstraintLayout>(R.id.layout_error)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        loaderList.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        viewModel.listArtist.observe(viewLifecycleOwner) {
            when (it) {
                is ArtistStateError -> {
                    loaderList.visibility = View.GONE
                    layoutError.visibility = View.VISIBLE
                }
                ArtistStateLoading -> {
                    loaderList.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                is ArtistStateSuccess -> {
                    loaderList.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    /*recyclerView.adapter = ListAdapterTrack(it.tracks as MutableList<Track>)
                    recyclerView.layoutManager = GridLayoutManager(context, 1)*/
                }
            }
        }
    }

}
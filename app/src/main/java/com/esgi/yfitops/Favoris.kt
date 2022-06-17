package com.esgi.yfitops

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment


class Favoris: Fragment() {

    private lateinit var btn_artist: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_favoris, container, false)
        btn_artist = view.findViewById(R.id.btn_redirection_artist)

        btn_artist.setOnClickListener {
                val i = Intent(context, ArtistActivity::class.java)
                startActivity(i)
        }
        //findNavController().navigate(R.id.tab_favorites)

        return  view
    }




}
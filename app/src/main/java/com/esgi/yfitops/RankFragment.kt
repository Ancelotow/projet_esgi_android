package com.esgi.yfitops

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.yfitops.models.entities.Rank
import com.esgi.yfitops.models.services.AlbumService
import com.esgi.yfitops.models.services.TrackService
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso


class RankFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_rank, container, false)
        val tabs = view.findViewById<TabLayout>(R.id.tabs)
        tabs.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab : TabLayout.Tab) {

                when (tab.position) {
                    0 -> changeFragment(TrackRankFragment.newInstance())
                    1 -> changeFragment(AlbumRankFragment.newInstance())
                }
            }
            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }
        })
        return view
    }

    fun changeFragment(view: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.rank_view, view)
            .commit()
    }

}

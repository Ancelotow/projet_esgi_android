package com.esgi.yfitops.models.services

import com.esgi.yfitops.models.entities.Rank
import com.esgi.yfitops.models.entities.Track
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TrackService {

    @GET("mostloved.php")
    fun listTracksRank(@Query("format") format: String = "track"): Call<Rank<Track>>

}
package com.esgi.yfitops.models.services

import com.esgi.yfitops.models.entities.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("mostloved.php")
    fun listTracksRank(@Query("format") format: String = "track"): Call<Rank<Track>>

    @GET("mostloved.php")
    fun listAlbumsRank(@Query("format") format: String = "album"): Call<Rank<Album>>

    @GET("search.php")
    fun searchArtist(@Query("s") search: String): Call<ListArtist>

}
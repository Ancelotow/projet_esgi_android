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

    @GET("searchalbum.php")
    fun searchAlbum(@Query("s") search: String): Call<ListAlbum>

    @GET("artist.php")
    fun getArtist(@Query("i") idArtist: Int): Call<ListArtist>

    @GET("album.php")
    fun getAlbumsFromArtist(@Query("i") idArtist: Int): Call<ListAlbum>

    @GET("track-top10.php")
    fun getTopTrackFromArtist(@Query("s") artistName: String): Call<ListTrack>

    @GET("track.php")
    fun getTrack(@Query("h") idTrack: Int): Call<ListTrack>

}
package com.esgi.yfitops.models.services

import com.esgi.yfitops.models.entities.Artist
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistService {

    @GET("/artist.php")
    fun listAlbumsRank(@Query("i") id: Int): Call<Artist>

}
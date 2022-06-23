package com.esgi.yfitops.models.services

import com.esgi.yfitops.models.entities.Album
import com.esgi.yfitops.models.entities.Rank
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AlbumService {

    @GET("mostloved.php")
    fun listAlbumsRank(@Query("format") format: String = "album"): Call<Rank<Album>>

}
package com.esgi.yfitops.models.entities

import com.esgi.yfitops.models.services.ApiConnection
import com.esgi.yfitops.models.services.ApiService
import com.google.gson.annotations.SerializedName
import retrofit2.await

class ListArtist(

    @SerializedName("artists")
    var artists: List<Artist>

) {

    companion object Service {

        suspend fun getArtistSearch(search: String): ListArtist {
            return ApiConnection.connection().create(ApiService::class.java).searchArtist(search)
                .await()
        }

    }

}
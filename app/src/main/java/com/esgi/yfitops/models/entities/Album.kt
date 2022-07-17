package com.esgi.yfitops.models.entities

import com.esgi.yfitops.models.services.ApiConnection
import com.esgi.yfitops.models.services.ApiService
import com.google.gson.annotations.SerializedName
import org.json.JSONObject
import retrofit2.await

class Album(

    @SerializedName("idAlbum")
    var id: Int,

    @SerializedName("idArtist")
    var idArtist: Int,

    @SerializedName("strAlbum")
    var name: String,

    @SerializedName("strArtist")
    var artist: String,

    @SerializedName("strAlbumThumb")
    var thumb: String?,

    @SerializedName("intYearReleased")
    var yearReleased: Int

) {

    companion object Service {

        suspend fun getAlbumsRanks(): List<Album> {
            val rankAlbums =
                ApiConnection.connection().create(ApiService::class.java).listAlbumsRank().await()
            return rankAlbums.listLoved
        }

    }

}
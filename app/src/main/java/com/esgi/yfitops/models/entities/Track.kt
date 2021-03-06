package com.esgi.yfitops.models.entities

import com.esgi.yfitops.models.services.ApiConnection
import com.esgi.yfitops.models.services.ApiService
import com.google.gson.annotations.SerializedName
import retrofit2.await

class Track(

    @SerializedName("idTrack")
    var id: Int,

    @SerializedName("idAlbum")
    var idAlbum: Int,

    @SerializedName("idArtist")
    var idArtist: Int,

    @SerializedName("strTrack")
    var title: String,

    @SerializedName("strArtist")
    var artist: String,

    @SerializedName("strTrackThumb")
    var thumb: String?,

    @SerializedName("strTrackLyrics")
    var lyrics: String?

) {

    companion object Service {

        suspend fun getTrackRank(): List<Track> {
            val rankTracks =
                ApiConnection.connection().create(ApiService::class.java).listTracksRank().await()
            return rankTracks.listLoved
        }

        suspend fun getTrack(idTrack: Int): Track? {
            val listTrack =
                ApiConnection.connection().create(ApiService::class.java).getTrack(idTrack).await()
            if(listTrack.track == null || listTrack.track!!.isEmpty()) {
                return null;
            }
            return listTrack.track!!.first()
        }

    }

}
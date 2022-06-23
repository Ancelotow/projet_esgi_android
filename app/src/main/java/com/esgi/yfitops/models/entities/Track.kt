package com.esgi.yfitops.models.entities

import com.esgi.yfitops.models.services.ApiConnection
import com.esgi.yfitops.models.services.TrackService
import com.google.gson.annotations.SerializedName
import org.json.JSONObject
import retrofit2.await

class Track(trackJson: JSONObject) {

    @SerializedName("idTrack")
    var id: Int

    @SerializedName("idAlbum")
    var idAlbum: Int

    @SerializedName("idArtist")
    var idArtist: Int

    @SerializedName("strTrack")
    var title: String

    @SerializedName("strArtist")
    var artist: String

    @SerializedName("strTrackThumb")
    var thumb: String

    init {
        this.id = trackJson.getInt("idTrack")
        this.idAlbum = trackJson.getInt("idAlbum")
        this.idArtist = trackJson.getInt("idArtist")
        this.title = trackJson.getString("strTrack")
        this.artist = trackJson.getString("strArtist")
        this.thumb = trackJson.getString("strTrackThumb")
        if (this.thumb == "null" || this.thumb == "") {
            this.thumb =
                "https://us.123rf.com/450wm/soloviivka/soloviivka1606/soloviivka160600001/59688426-music-note-vecteur-icône-blanc-sur-fond-noir.jpg"
        }
    }

    companion object Service {

        suspend fun getTrackRank(): List<Track> {
            val rankTracks =
                ApiConnection.connection().create(TrackService::class.java).listTracksRank().await()
            return rankTracks.listLoved
        }

    }

}
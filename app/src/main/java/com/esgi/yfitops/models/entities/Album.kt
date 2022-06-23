package com.esgi.yfitops.models.entities

import com.esgi.yfitops.models.services.AlbumService
import com.esgi.yfitops.models.services.ApiConnection
import com.google.gson.annotations.SerializedName
import org.json.JSONObject
import retrofit2.await

class Album(albumJson: JSONObject) {

    @SerializedName("idAlbum")
    var id: Int

    @SerializedName("idArtist")
    var idArtist: Int

    @SerializedName("strAlbum")
    var name: String

    @SerializedName("strArtist")
    var artist: String

    /*@SerializedName("intYearReleased")
    var yearRelease: Int*/

    @SerializedName("strAlbumThumb")
    var thumb: String

    init {
        this.id = albumJson.getInt("idAlbum")
        this.idArtist = albumJson.getInt("idArtist")
        this.name = albumJson.getString("strAlbum")
        this.artist = albumJson.getString("strArtist")
        //this.yearRelease = albumJson.getInt("intYearReleased")
        this.thumb = albumJson.getString("strAlbumThumb")
        if (this.thumb == "null" || this.thumb == "") {
            this.thumb =
                "https://img.pixers.pics/pho_wat(s3:700/FO/59/05/58/16/700_FO59055816_1fa9cde3d60f0826a2e989df0cf0671b.jpg,700,700,cms:2018/10/5bd1b6b8d04b8_220x50-watermark.png,over,480,650,jpg)/posters-disque-vinyle.jpg.jpg"
        }
    }

    companion object Service {

        suspend fun getAlbumsRanks(): List<Album> {
            val rankAlbums =
                ApiConnection.connection().create(AlbumService::class.java).listAlbumsRank().await()
            return rankAlbums.listLoved
        }

    }

}
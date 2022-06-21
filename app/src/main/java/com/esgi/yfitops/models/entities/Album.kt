package com.esgi.yfitops.models.entities

import org.json.JSONObject

class Album(albumJson: JSONObject) {

    var id: Int = 0;
    var idArtist: Int = 0;
    var name: String = "";
    var artist: String = "";
    var dateY: String = "";
    var thumb: String =
        "https://img.pixers.pics/pho_wat(s3:700/FO/59/05/58/16/700_FO59055816_1fa9cde3d60f0826a2e989df0cf0671b.jpg,700,700,cms:2018/10/5bd1b6b8d04b8_220x50-watermark.png,over,480,650,jpg)/posters-disque-vinyle.jpg.jpg";

    init {
        this.id = albumJson.getInt("idAlbum")
        this.idArtist = albumJson.getInt("idArtist")
        this.name = albumJson.getString("strAlbum")
        this.artist = albumJson.getString("strArtist")
        this.dateY = albumJson.getString("intYearReleased")
        this.thumb = albumJson.getString("strAlbumThumb")
        if (this.thumb == "null" || this.thumb == "") {
            this.thumb =
                "https://img.pixers.pics/pho_wat(s3:700/FO/59/05/58/16/700_FO59055816_1fa9cde3d60f0826a2e989df0cf0671b.jpg,700,700,cms:2018/10/5bd1b6b8d04b8_220x50-watermark.png,over,480,650,jpg)/posters-disque-vinyle.jpg.jpg"
        }
    }

    fun toRank(): Rank {
        return Rank(id, name, artist, thumb)
    }

    fun toAlbumDet(): AlbumDet {
        return AlbumDet(id, name, dateY)
    }

}
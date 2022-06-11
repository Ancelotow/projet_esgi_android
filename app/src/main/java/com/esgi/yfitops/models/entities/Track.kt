package com.esgi.yfitops.models.entities
import org.json.JSONObject

class Track(trackJson: JSONObject) {

    private var id: Int = 0;
    private var idAlbum: Int = 0;
    private var idArtist: Int = 0;
    private var title: String = "";
    private var artist: String = "";
    private var thumb: String =
        "https://play-lh.googleusercontent.com/G8GiOrGYRfF58WhvqTxm1vbrV5cLQd-Yw7Mls_vjTjr_z3sSDxiRUuPER7HOPZj4VQY=w240-h480";

    init {
        this.id = trackJson.getInt("idTrack")
        this.idAlbum = trackJson.getInt("idAlbum")
        this.idArtist = trackJson.getInt("idArtist")
        this.title = trackJson.getString("strTrack")
        this.title = trackJson.getString("strTrack")
        this.artist = trackJson.getString("strArtist")
        this.thumb = trackJson.getString("strTrackThumb")
    }

}
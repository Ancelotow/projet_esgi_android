package com.esgi.yfitops.models.entities
import org.json.JSONObject

class Track(trackJson: JSONObject) {

    var id: Int = 0;
    var idAlbum: Int = 0;
    var idArtist: Int = 0;
    var title: String = "";
    var artist: String = "";
    var thumb: String =
        "https://us.123rf.com/450wm/soloviivka/soloviivka1606/soloviivka160600001/59688426-music-note-vecteur-icône-blanc-sur-fond-noir.jpg";

    init {
        this.id = trackJson.getInt("idTrack")
        this.idAlbum = trackJson.getInt("idAlbum")
        this.idArtist = trackJson.getInt("idArtist")
        this.title = trackJson.getString("strTrack")
        this.artist = trackJson.getString("strArtist")
        this.thumb = trackJson.getString("strTrackThumb")
        if(this.thumb == "null" || this.thumb == "") {
            this.thumb = "https://us.123rf.com/450wm/soloviivka/soloviivka1606/soloviivka160600001/59688426-music-note-vecteur-icône-blanc-sur-fond-noir.jpg"
        }
    }

    fun toRank(): Rank {
        return Rank(id, title, artist, thumb)
    }

}
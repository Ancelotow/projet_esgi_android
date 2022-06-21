package com.esgi.yfitops.models.entities

import org.json.JSONObject

class Artist (trackJson: JSONObject){

    var idArtist: Int = 0;
    var artist: String = "";
    var strStyle: String = ""
    var biography: String=""
    var thumb: String =
        "https://us.123rf.com/450wm/soloviivka/soloviivka1606/soloviivka160600001/59688426-music-note-vecteur-icône-blanc-sur-fond-noir.jpg";

    init {
        this.idArtist = trackJson.getInt("idArtist")
        this.artist = trackJson.getString("strArtist")
        this.strStyle = trackJson.getString("strStyle")
        this.biography = trackJson.getString("strBiographyEN")
        this.thumb = trackJson.getString("strArtistThumb")
        if(this.thumb == "null" || this.thumb == "") {
            this.thumb = "https://us.123rf.com/450wm/soloviivka/soloviivka1606/soloviivka160600001/59688426-music-note-vecteur-icône-blanc-sur-fond-noir.jpg"
        }
    }
}
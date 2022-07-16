package com.esgi.yfitops.models.entities

import com.google.gson.annotations.SerializedName

data class ListAlbum(

    @SerializedName("album")
    var album: List<Album>

)
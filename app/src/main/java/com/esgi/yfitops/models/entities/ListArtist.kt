package com.esgi.yfitops.models.entities

import com.google.gson.annotations.SerializedName

data class ListArtist(

    @SerializedName("artists")
    var artists: List<Artist>?

)
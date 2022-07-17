package com.esgi.yfitops.models.entities

import com.google.gson.annotations.SerializedName

data class ListTrack(

    @SerializedName("track")
    var track: List<Track>?

)
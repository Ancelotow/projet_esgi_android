package com.esgi.yfitops.models.entities

import com.google.gson.annotations.SerializedName

class Rank<T>(listLoved: List<T>) {

    @SerializedName("loved")
    var listLoved: List<T>

    init {
        this.listLoved = listLoved
    }

}
package com.esgi.yfitops.models.entities

class Rank(id: Int, title: String, subTitle: String, thumb: String) {

    var id: Int = 0
    var title: String = ""
    var subTitle: String = ""
    var thumb: String = ""

    init {
        this.id = id
        this.title = title
        this.subTitle = subTitle
        this.thumb = thumb
    }

}
package com.esgi.yfitops.models.entities

import android.util.Log
import com.esgi.yfitops.models.services.ApiConnection
import com.esgi.yfitops.models.services.ApiService
import com.google.gson.annotations.SerializedName
import retrofit2.await
import java.util.*

class Artist(

    @SerializedName("idArtist")
    var id: Int,

    @SerializedName("strArtist")
    var nameArtist: String,

    @SerializedName("strStyle")
    var style: String,

    @SerializedName("strBiographyEN")
    var biographyEN: String,

    @SerializedName("strBiographyDE")
    var biographyDE: String,

    @SerializedName("strBiographyFR")
    var biographyFR: String,

    @SerializedName("strBiographyCN")
    var biographyCN: String,

    @SerializedName("strBiographyIT")
    var biographyIT: String,

    @SerializedName("strBiographyJP")
    var biographyJP: String,

    @SerializedName("strBiographyRU")
    var biographyRU: String,

    @SerializedName("strBiographyES")
    var biographyES: String,

    @SerializedName("strBiographyPT")
    var biographyPT: String,

    @SerializedName("strBiographySE")
    var biographySE: String,

    @SerializedName("strBiographyNL")
    var biographyNL: String,

    @SerializedName("strBiographyHU")
    var biographyHU: String,

    @SerializedName("strBiographyNO")
    var biographyNO: String,

    @SerializedName("strBiographyIL")
    var biographyIL: String,

    @SerializedName("strBiographyPL")
    var biographyPL: String,

    @SerializedName("strArtistThumb")
    var thumb: String?,

    @SerializedName("strArtistLogo")
    var logo: String?,

    @SerializedName("strCountry")
    var country: String,

    @SerializedName("strGenre")
    var genre: String

) {

    fun getDescription(): String {
        return when(Locale.getDefault().isO3Language) {
            "fra" -> biographyFR
            "ita" -> biographyIT
            "jpn" -> biographyJP
            "deu" -> biographyDE
            "rus" -> biographyRU
            "spa" -> biographyES
            "por" -> biographyPT
            "sme" -> biographySE
            "nld" -> biographyNL
            "hun" -> biographyHU
            "nor" -> biographyNO
            "pol" -> biographyPL
            else -> biographyEN
        }
    }

}
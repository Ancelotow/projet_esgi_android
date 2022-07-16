package com.esgi.yfitops.models.entities

import com.esgi.yfitops.models.services.ApiConnection
import com.esgi.yfitops.models.services.ApiService
import retrofit2.await

class Search(

    var resultArtists: ListArtist,
    var resultAlbums: ListAlbum

) {

    fun getListResult(): MutableList<Any> {
        val listResult = mutableListOf<Any>()
        listResult.addAll(resultArtists.artists)
        if(resultAlbums.album != null) {
            listResult.addAll(resultAlbums.album)
        }
        return listResult
    }

    companion object Service {

        suspend fun getSearch(searchStr: String): Search {
            val artists = ApiConnection.connection().create(ApiService::class.java).searchArtist(searchStr)
                .await()
            val albums = ApiConnection.connection().create(ApiService::class.java).searchAlbum(searchStr)
                .await()
            return Search(artists, albums)
        }

    }

}
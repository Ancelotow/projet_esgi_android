package com.esgi.yfitops.models.entities

import com.esgi.yfitops.models.services.ApiConnection
import com.esgi.yfitops.models.services.ApiService
import retrofit2.await

class AlbumDetail (
    val information: Album,
    val tracks: ListTrack
    ) {

    companion object Service {

        suspend fun getAlbumId(idAlbum: Int): AlbumDetail? {
            val album = ApiConnection.connection().create(ApiService::class.java).getAlbumId(idAlbum).await()
            if(album.album == null) {
                return null
            } else if(album.album!!.isEmpty()) {
                return null
            }
            val albumInfo = album.album!!.first()
            val tracks = ApiConnection.connection().create(ApiService::class.java).getTrackFromAlbum(idAlbum).await()
            return AlbumDetail(albumInfo, tracks)
        }

    }
}
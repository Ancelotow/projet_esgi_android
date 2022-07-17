package com.esgi.yfitops.models.entities

import com.esgi.yfitops.models.services.ApiConnection
import com.esgi.yfitops.models.services.ApiService
import retrofit2.await

class ArtistDetail(

    val information: Artist,
    val albums: ListAlbum,
    val topTracks: ListTrack

) {

    companion object Service {

        suspend fun getArtistInfo(idArtist: Int): ArtistDetail? {
            val artist = ApiConnection.connection().create(ApiService::class.java).getArtist(idArtist).await()
            if(artist.artists == null) {
                return null
            } else if(artist.artists!!.isEmpty()) {
                return null
            }
            val artistInfo = artist.artists!!.first()
            val listAlbums = ApiConnection.connection().create(ApiService::class.java).getAlbumsFromArtist(idArtist).await()
            val topTracks = ApiConnection.connection().create(ApiService::class.java).getTopTrackFromArtist(artistInfo.nameArtist).await()
            return ArtistDetail(artistInfo, listAlbums, topTracks)
        }

    }

}
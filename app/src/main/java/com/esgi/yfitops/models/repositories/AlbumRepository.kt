package com.esgi.yfitops.models.repositories

import com.esgi.yfitops.models.entities.Album
import com.esgi.yfitops.models.entities.AlbumDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object AlbumRepository {

    suspend fun fetchAlbumsRank(): Flow<AlbumState> {
        return flow {
            emit(AlbumStateLoading)
            try {
                emit(AlbumStateSuccess(Album.getAlbumsRanks()))
            } catch (e: Exception) {
                emit(AlbumStateError(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun fetchAlbumsId(idAlbum: Int): Flow<AlbumDetailState> {
        return flow {
            emit(AlbumDetailStateLoading)
            try {
                emit(AlbumDetailStateSuccess(AlbumDetail.getAlbumId(idAlbum)))
            } catch (e: Exception) {
                emit(AlbumDetailStateError(e))
            }
        }.flowOn(Dispatchers.IO)
    }

}

sealed class AlbumState
object AlbumStateLoading: AlbumState()
data class AlbumStateSuccess(val albums: List<Album>): AlbumState()
data class AlbumStateError(val ex: java.lang.Exception): AlbumState()

sealed class AlbumDetailState
object AlbumDetailStateLoading: AlbumDetailState()
data class AlbumDetailStateSuccess(val album: AlbumDetail?): AlbumDetailState()
data class AlbumDetailStateError(val ex: java.lang.Exception): AlbumDetailState()
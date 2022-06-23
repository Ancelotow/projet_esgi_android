package com.esgi.yfitops.models.repositories

import com.esgi.yfitops.models.entities.Album
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

}

sealed class AlbumState
object AlbumStateLoading: AlbumState()
data class AlbumStateSuccess(val albums: List<Album>): AlbumState()
data class AlbumStateError(val ex: java.lang.Exception): AlbumState()
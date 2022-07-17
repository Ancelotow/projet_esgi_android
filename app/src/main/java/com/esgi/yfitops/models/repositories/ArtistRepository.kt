package com.esgi.yfitops.models.repositories

import com.esgi.yfitops.models.entities.ArtistDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object ArtistRepository {

    suspend fun fetchArtist(idArtist: Int): Flow<ArtistState> {
        return flow {
            emit(ArtistStateLoading)
            try {
                emit(ArtistStateSuccess(ArtistDetail.getArtistInfo(idArtist)))
            } catch (e: Exception) {
                emit(ArtistStateError(e))
            }
        }.flowOn(Dispatchers.IO)
    }

}

sealed class ArtistState
object ArtistStateLoading: ArtistState()
data class ArtistStateSuccess(val artist: ArtistDetail?): ArtistState()
data class ArtistStateError(val ex: java.lang.Exception): ArtistState()
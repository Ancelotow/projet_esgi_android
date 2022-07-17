package com.esgi.yfitops.models.repositories

import com.esgi.yfitops.models.entities.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


object LyricRepository {

    suspend fun fetchTrack(idTrack: Int): Flow<LyricState> {
        return flow {
            emit(LyricStateLoading)
            try {
                emit(LyricStateSuccess(Track.getTrack(idTrack)))
            } catch (e: Exception) {
                emit(LyricStateError(e))
            }
        }.flowOn(Dispatchers.IO)
    }

}

sealed class LyricState
object LyricStateLoading: LyricState()
data class LyricStateSuccess(val track: Track?): LyricState()
data class LyricStateError(val ex: java.lang.Exception): LyricState()
package com.esgi.yfitops.models.repositories

import com.esgi.yfitops.models.entities.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object TrackRepository {

    suspend fun fetchTracksRank(): Flow<TrackState> {
        return flow {
            emit(TrackStateLoading)
            try {
                emit(TrackStateSuccess(Track.getTrackRank()))
            } catch (e: Exception) {
                emit(TrackStateError(e))
            }
        }.flowOn(Dispatchers.IO)
    }

}

sealed class TrackState
object TrackStateLoading: TrackState()
data class TrackStateSuccess(val tracks: List<Track>): TrackState()
data class TrackStateError(val ex: java.lang.Exception): TrackState()
package com.esgi.yfitops.models.repositories

import com.esgi.yfitops.models.entities.ListArtist
import com.esgi.yfitops.models.entities.Search
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


object SearchRepository {

    suspend fun fetchSearch(search: String): Flow<SearchState> {
        return flow {
            emit(SearchStateLoading)
            try {
                emit(SearchStateSuccess(Search.getSearch(search)))
            } catch (e: Exception) {
                emit(SearchStateError(e))
            }
        }.flowOn(Dispatchers.IO)
    }

}

sealed class SearchState
object SearchStateLoading: SearchState()
data class SearchStateSuccess(val search: Search): SearchState()
data class SearchStateError(val ex: java.lang.Exception): SearchState()
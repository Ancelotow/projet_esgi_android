package com.esgi.yfitops.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esgi.yfitops.models.repositories.AlbumRepository
import com.esgi.yfitops.models.repositories.AlbumState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AlbumRankViewModel : ViewModel() {

    private val _albums = MutableLiveData<AlbumState>()
    val listAlbums = _albums

    init {
        getAlbumsRank()
    }

    fun getAlbumsRank() {
        viewModelScope.launch {
            AlbumRepository.fetchAlbumsRank().collect {
                _albums.value = it
            }
        }
    }

}

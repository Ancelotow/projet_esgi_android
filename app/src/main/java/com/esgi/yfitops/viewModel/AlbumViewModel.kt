package com.esgi.yfitops.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esgi.yfitops.models.repositories.AlbumDetailState
import com.esgi.yfitops.models.repositories.AlbumRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AlbumViewModel: ViewModel()  {

    private val _album = MutableLiveData<AlbumDetailState>()
    val album = _album

    fun getAlbumId(idAlbum: Int) {
        viewModelScope.launch {
            AlbumRepository.fetchAlbumsId(idAlbum).collect {
                _album.value = it
            }
        }
    }
}
package com.esgi.yfitops.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esgi.yfitops.models.repositories.ArtistRepository
import com.esgi.yfitops.models.repositories.ArtistState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _artists = MutableLiveData<ArtistState>()
    val listArtist = _artists

    init {
        getArtists("")
    }

    fun getArtists(search: String) {
        viewModelScope.launch {
            ArtistRepository.fetchArtists(search).collect {
                _artists.value = it
            }
        }
    }

}
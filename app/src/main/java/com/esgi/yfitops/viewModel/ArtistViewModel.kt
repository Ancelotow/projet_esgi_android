package com.esgi.yfitops.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esgi.yfitops.models.repositories.ArtistRepository
import com.esgi.yfitops.models.repositories.ArtistState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ArtistViewModel : ViewModel() {

    private val _artist = MutableLiveData<ArtistState>()
    val artist = _artist

    fun getArtist(idArtist: Int) {
        viewModelScope.launch {
            ArtistRepository.fetchArtist(idArtist).collect {
                _artist.value = it
            }
        }
    }

}
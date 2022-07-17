package com.esgi.yfitops.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esgi.yfitops.models.repositories.TrackRepository
import com.esgi.yfitops.models.repositories.TrackState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TrackRankViewModel : ViewModel() {

    private val _tracks = MutableLiveData<TrackState>()
    val listTrack = _tracks

    init {
        getAlbumsRank()
    }

    fun getAlbumsRank() {
        viewModelScope.launch {
            TrackRepository.fetchTracksRank().collect {
                _tracks.value = it
            }
        }
    }

}
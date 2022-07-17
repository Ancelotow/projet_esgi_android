package com.esgi.yfitops.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esgi.yfitops.models.repositories.LyricRepository
import com.esgi.yfitops.models.repositories.LyricState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LyricViewModel : ViewModel() {

    private val _track = MutableLiveData<LyricState>()
    val track = _track

    fun getTrack(idTrack: Int) {
        viewModelScope.launch {
            LyricRepository.fetchTrack(idTrack).collect {
                _track.value = it
            }
        }
    }

}
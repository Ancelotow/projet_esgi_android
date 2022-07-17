package com.esgi.yfitops.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esgi.yfitops.models.repositories.SearchRepository
import com.esgi.yfitops.models.repositories.SearchState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _search = MutableLiveData<SearchState>()
    val search = _search

    init {
        getSearch("")
    }

    fun getSearch(search: String) {
        viewModelScope.launch {
            SearchRepository.fetchSearch(search).collect {
                _search.value = it
            }
        }
    }

}
package com.example.wallpaperapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.data.api.model.WallpaperData
import com.example.wallpaperapp.domain.repositories.PicSumRepository
import com.example.wallpaperapp.presentation.WallpaperUiState
import com.example.wallpaperapp.utils.ResultData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: PicSumRepository
) : ViewModel() {

    // livedata -> persisted
// 2 options
    //1. livedata of wallpaperList
    //2. Holder of Data

    private val _wallpaperList: MutableStateFlow<WallpaperUiState> =
        MutableStateFlow(WallpaperUiState.Loading)

    val wallpaperList get() = _wallpaperList.asStateFlow()

    fun fetchWallpapers() {
        viewModelScope.launch(Dispatchers.IO) {
            // we do our API call Repository

            repository.getImagesURLFromApi().collect { result ->
                when (result) {
                    is ResultData.Error -> {
                        _wallpaperList.update { WallpaperUiState.Error(result.message.orEmpty()) }
                    }
                    is ResultData.Success -> {
                        if (!result.data.isNullOrEmpty()) {
                            _wallpaperList.update { WallpaperUiState.Success(result.data) }
                        } else {
                            _wallpaperList.update { WallpaperUiState.EmptyList }
                        }
                    }
                }
            }
        }
    }


}
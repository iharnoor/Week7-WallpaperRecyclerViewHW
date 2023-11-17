package com.example.wallpaperapp.presentation

import com.example.wallpaperapp.domain.entities.PicSumWallpaperURL

sealed class WallpaperUiState {

    object Loading : WallpaperUiState()
    object EmptyList : WallpaperUiState()

    data class Success(val data: List<PicSumWallpaperURL>) : WallpaperUiState()
    data class Error(val message: String) : WallpaperUiState()
}
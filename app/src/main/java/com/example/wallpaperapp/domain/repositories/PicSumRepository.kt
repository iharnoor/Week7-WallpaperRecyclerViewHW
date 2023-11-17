package com.example.wallpaperapp.domain.repositories

import com.example.wallpaperapp.domain.entities.PicSumWallpaperURL
import com.example.wallpaperapp.utils.ResultData
import kotlinx.coroutines.flow.Flow

interface PicSumRepository {
    fun getImagesURLFromApi() : Flow<ResultData<List<PicSumWallpaperURL>>>

}
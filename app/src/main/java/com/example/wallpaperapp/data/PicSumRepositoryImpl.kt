package com.example.wallpaperapp.data

import android.net.http.HttpException
import android.os.Build
import com.example.wallpaperapp.data.api.PicSumAPI
import com.example.wallpaperapp.domain.entities.PicSumWallpaperURL
import com.example.wallpaperapp.domain.repositories.PicSumRepository
import com.example.wallpaperapp.utils.ResultData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class PicSumRepositoryImpl @Inject constructor(
    val api: PicSumAPI
) : PicSumRepository {
    override fun getImagesURLFromApi(): Flow<ResultData<List<PicSumWallpaperURL>>> = flow {
        if (Build.VERSION.SDK_INT >= 34) {
            try {
                val response = api.getWallpaperImageData(1)
                val outputList: List<PicSumWallpaperURL>

                response?.let {
                    outputList = response.map {
                        PicSumWallpaperURL(it.downloadUrl.orEmpty())
                    }
                    emit(ResultData.Success(outputList))
                }
            } catch (e: HttpException) {
                emit(ResultData.Error(e.message))
            } catch (e: IOException) {
                emit(ResultData.Error(e.message))
            }
        }
    }
}
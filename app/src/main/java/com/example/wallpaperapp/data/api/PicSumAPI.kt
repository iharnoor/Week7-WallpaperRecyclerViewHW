package com.example.wallpaperapp.data.api

import com.example.wallpaperapp.WallpaperApp
import com.example.wallpaperapp.data.api.model.WallpaperData
import com.example.wallpaperapp.utils.Constants.END_POINT
import retrofit2.http.GET
import retrofit2.http.Query

interface PicSumAPI {
    @GET(END_POINT)
    suspend fun getWallpaperImageData(
            @Query("page") page:Int, @Query("limit") limit:Int = 100
    ): List<WallpaperData>? // no data received
}
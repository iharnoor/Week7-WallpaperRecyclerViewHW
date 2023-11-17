package com.example.wallpaperapp.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.wallpaperapp.data.PicSumRepositoryImpl
import com.example.wallpaperapp.data.api.PicSumAPI
import com.example.wallpaperapp.domain.repositories.PicSumRepository
import com.example.wallpaperapp.utils.Constants
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    companion object { // static member so consistent value in entire program
        @Provides
        @Singleton
        fun bindApi() = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.END_POINT)
            .build().create(PicSumAPI::class.java);
    }

    @Binds
    @Singleton
    abstract fun bindPicSumRepository(repositoryImpl: PicSumRepositoryImpl): PicSumRepository
}
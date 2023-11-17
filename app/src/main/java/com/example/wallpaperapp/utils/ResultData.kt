package com.example.wallpaperapp.utils

sealed class ResultData<out T> {
    data class Success<out T>(val data: T? = null): ResultData<T>()
    data class Error(val message: String? = null): ResultData<Nothing>()
}
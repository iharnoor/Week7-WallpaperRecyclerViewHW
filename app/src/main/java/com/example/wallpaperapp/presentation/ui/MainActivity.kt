package com.example.wallpaperapp.presentation.ui

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.wallpaperapp.databinding.ActivityMainBinding
import com.example.wallpaperapp.domain.entities.PicSumWallpaperURL
import com.example.wallpaperapp.presentation.WallpaperUiState
import com.example.wallpaperapp.presentation.adapter.ImagesRecyclerViewAdapter
import com.example.wallpaperapp.presentation.adapter.WallPaperClickListener
import com.example.wallpaperapp.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), WallPaperClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var wallpaperAdapter: ImagesRecyclerViewAdapter
    private val wallpaperViewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViews()
        collectWallpaperState()
        wallpaperViewModel.fetchWallpapers()
    }

    private fun collectWallpaperState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                wallpaperViewModel.wallpaperList.collect { state ->
                    when (state) {
                        WallpaperUiState.EmptyList -> {
                            binding.loader.isVisible = false // no wallpper found
                        }

                        is WallpaperUiState.Error -> {
                            binding.loader.isVisible = false // some error and show toast pass sat
                        }

                        WallpaperUiState.Loading -> {
                            binding.loader.isVisible = true // some error and show toast
                        }

                        is WallpaperUiState.Success -> populateData(state.data)
                    }

                }
            }
        }
    }

    private fun setUpViews() {
        binding.imagesRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this@MainActivity, 2)
        }
    }

    private fun populateData(picSumWallpaperURLs: List<PicSumWallpaperURL>) {
        binding.loader.isVisible = false
        wallpaperAdapter = ImagesRecyclerViewAdapter(picSumWallpaperURLs, this)
        binding.imagesRecyclerView.adapter = wallpaperAdapter
    }

    override fun onWallpaperClick(imageURL: String) {
        // UI
        CoroutineScope(Dispatchers.Main).launch {
            val bitmap = getBitmapFromUrl(imageURL)
            setWallpaper(bitmap)
            Toast.makeText(this@MainActivity, "Wallpaper Set successfully", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private suspend fun getBitmapFromUrl(url: String): Bitmap = withContext(Dispatchers.IO) {
        return@withContext try {
            val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .priority(Priority.HIGH).dontAnimate().dontTransform()
            Glide.with(this@MainActivity).asBitmap().load(url).apply(requestOptions).submit().get()
        } catch (e: Exception) {
            throw IOException("Failed to get bitmap from URL: $url", e)
        }
    }

    override fun setWallpaper(bitmap: Bitmap) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val wallpaperManager = WallpaperManager.getInstance(this@MainActivity)
                wallpaperManager.setBitmap(bitmap)
            }

        }
    }

}
